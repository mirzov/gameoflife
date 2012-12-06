function runTheApp(options){
	setupWebsockets(options.pathToWebSocket);
	var mainVm = makeMainVm(10);
	ko.applyBindings(mainVm);
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
	//console.log(server_message);
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
}

function makeMainVm(size){
	var self = {};
	self.lifeFieldRows = [];
	for(var rowi = 0; rowi < size; rowi++){
		var row = [];
		for(var celli = 0; celli < size; celli++){
			row.push(new CellVm());
		}
		self.lifeFieldRows.push(row);
	}
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