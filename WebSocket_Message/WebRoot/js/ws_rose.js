/**
 * rose 连接服务器websocket js文件
 */

var websocket = '';
var ajaxPageNum = 1;
var last_health;
var health_timeout = 10;
var tDates = [], tData = [];
var rightIndex;

if (window.WebSocket) {
    websocket = new WebSocket(
            encodeURI('ws://' + document.domain + ':8888'));
    websocket.onopen = function() {
        console.log('已连接');
        websocket.send("onlinerose");
        heartbeat_timer = setInterval(function() {
            keepalive(websocket)
        }, 60000);
    };
    websocket.onerror = function() {
        console.log('连接发生错误');
    };
    websocket.onclose = function() {
        console.log('已经断开连接');
        initWs();
    };
    // 消息接收
    websocket.onmessage = function(message) {
        console.log(message) // type: string...
        console.log(message.data)
        
        // 未读消失， 对方已经收到消息
        if (message.data == "yes") {
        	$(".isRead").text("");
        	return;
        }       
        
        var jsonMsg = JSON.parse(message.data);
        
        // 收到消息改状态
        $.post("UnReadMsg", 
        	{"fromName":jsonMsg.toName, "toName": jsonMsg.fromName}, 
        	function(data) {}
        );
        
        // 向服务器发送消息 rose 已经收到消息
        $.ajax({
        	url: "IsReadMsg",
        	type: "post",
        	data: {"fromName":jsonMsg.toName, "toName": jsonMsg.fromName, "isRead": "yes"},
        	success: function(data) {
        		//alert("success");
        	},
        	async: true
        });
        
        appendMsg(jsonMsg, $("#msgListLeft"));
    };
} else {
    alert("该浏览器不支持提醒。<br/>建议使用高版本的浏览器，<br/>如 IE10、火狐 、谷歌  、搜狗等");
}

var initWs = function() {
    if (window.WebSocket) {
        websocket = new WebSocket(
                encodeURI('ws://' + document.domain + ':8888'));
        websocket.onopen = function() {
            console.log('已连接');
            websocket.send("onlinerose");
            heartbeat_timer = setInterval(function() {
                keepalive(websocket)
            }, 60000);
        };
        websocket.onerror = function() {
            console.log('连接发生错误');
        };
        websocket.onclose = function() {
            console.log('已经断开连接');
            initWs();
        };
        // 消息接收
        websocket.onmessage = function(message) {
            console.log(message)
            console.log(message.data)
            //alert("有人在扫码！")
            appendMsg(JSON.parse(message.data),$("#msgListLeft"));
        };
    } else {
        alert("该浏览器不支持提醒。<br/>建议使用高版本的浏览器，<br/>如 IE10、火狐 、谷歌  、搜狗等");
    }
}

// 心跳包
function keepalive(ws) {
    var time = new Date();
    if (last_health != -1 && (time.getTime() - last_health > health_timeout)) {
        // ws.close();
    } else {
        if (ws.bufferedAmount == 0) {
            ws.send('connenct again from rose...');
        }
    }
}