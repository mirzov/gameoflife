function runTheApp(options){
	var conn = setupWebsockets(options.pathToWebSocket);
	var field = makeLifeFieldVm(options.fieldSize);
	var mainVm = makeMainVm(conn, field);
	ko.applyBindings(mainVm);
}

function makeMainVm(connection, lifeFieldVm){
	var self = {};
	self.lifeFieldVm = lifeFieldVm;
	return self;
}

function setupWebsockets(pathToWebSocket){
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
		var server_message = e.data;
		$("#field").val(server_message);
	}
	
	$("#send").on("click", function(){
		var message = $('#message').val();
		//console.log('sending ' + message);
		connection.send(message);
	});
	
	$("#close").on("click", function(){
		console.log('Closing connection');
		connection.close();
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
				if(lifeFieldRows[i][j].live()){
					res.push([i,j]);
				}
			}
		}
		return res;
	};
	
	self.killAll = function(){
		for(var i = 0; i < size; i++){
			for(var j = 0; j < size; j++){
				lifeFieldRows[i][j].live(false);
			}
		}
	};
	
	self.init = function(liveCellsList){
		self.killAll();
		liveCellsList.forEach(function(cell){
			lifeFieldRows[cell[0]][cell[1]].live(true);
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