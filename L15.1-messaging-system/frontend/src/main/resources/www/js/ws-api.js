WsApi = function(url) {
    this.url = url;

    let ws;
    let requests = {};
    let id = 0;

    let onMessage = function (data) {
        let response = JSON.parse(data.data);

        if (requests.hasOwnProperty(response.replyTo)) {
            let request = requests[response.replyTo];

            delete requests[response.replyTo];

            if(response.ok){
                request.resolve(response.data);
            }else{
                request.reject(response.data.code,response.data.msg);
            }
        }
    };

    this.connect = function(){
        return new Promise(function(resolve, reject) {
            ws = new WebSocket(url);
            ws.onmessage = onMessage;

            ws.onopen = function() {
                resolve();
            };
            ws.onerror = function(err) {
                reject(err);
            };
        });
    };

    this.callCommand = function(name,params){
        let callId = id++;

        let promise = new Promise(function(resolve, reject){
            requests[callId] = {
                "resolve":resolve,
                "reject":reject
            };
        });

        ws.send(JSON.stringify(
            {
                "id":callId,
                "type": "command",
                "data":{
                    "name":name,
                    ...params
                }
            }
        ));

        return promise;
    }
}