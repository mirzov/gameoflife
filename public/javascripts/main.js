function runTheApp(options){
	var field = makeLifeFieldVm(options.fieldSize);
	var conn = setupWebsockets(options.pathToWebSocket, field);
	var mainVm = makeMainVm(conn, field);
	ko.applyBindings(mainVm);
}

function makeMainVm(connection, lifeFieldVm){
	var self = {};
	self.lifeFieldVm = lifeFieldVm;
	return self;
}

function setupWebsockets(pathToWebSocket, fieldVm){
	var connection = new WebSocket(pathToWebSocket);
	connection.onopen = function(){
		console.log('Connection open!');
	}
	connection.onclose = function(){
		console.log('Connection closed');
	}
	connection.onerror = function(error){
		console.log('Error detected: ' + error);
	}
	connection.onmessage = function(e){
		if(e.data){
			fieldVm.init(JSON.parse(e.data).life);
		}
	}
	
	$("#start").on("click", function(){
		var message = JSON.stringify({
			startGen: fieldVm.listLiveCells(),
			steps: 1000,
			pause: 500
		});
		//console.log('sending ' + message);
		connection.send(message);
	});
	
	$("#stop").on("click", function(){
		connection.send(JSON.stringify({command: 'exit'}));
	});
	return connection;
}

function makeLifeFieldVm(size){
	var self = {};
	
	self.lifeFieldRows = [];
	for(var rowi = 0; rowi < size; rowi++){
		var row = [];
		for(var celli = 0; celli < size; celli++){
			row.push(new CellVm());
		}
		self.lifeFieldRows.push(row);
	}
	
	self.listLiveCells = function(){
		var res = [];
		for(var i = 0; i < size; i++){
			for(var j = 0; j < size; j++){
				if(self.lifeFieldRows[i][j].live()){
					res.push({x: i, y: j});
				}
			}
		}
		return res;
	};
	
	self.killAll = function(){
		for(var i = 0; i < size; i++){
			for(var j = 0; j < size; j++){
				self.lifeFieldRows[i][j].live(false);
			}
		}
	};
	
	self.init = function(liveCellsList){
		self.killAll();
		if(!liveCellsList) return;
		liveCellsList.forEach(function(cell){
			var x = cell.x;
			var y = cell.y;
			//console.log('initing ' + JSON.stringify(cell));
			if(x >= 0 && y >= 0 && x < size && y < size){
				self.lifeFieldRows[x][y].live(true);
			}
		});
	};
	
	return self;
};

function CellVm(){
	var cell = {};
	cell.live = ko.observable(false);
	cell.cellclass = ko.computed(function(){
		if(cell.live()) return 'livecell';
		return 'deadcell';
	});
	cell.cellclick = function(){
		cell.live(!cell.live())
	};
	return cell;
}