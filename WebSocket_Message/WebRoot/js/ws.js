/**
 * websocket����
 */

user = prompt("pls fill current user!")

var websocket = '';
var ajaxPageNum = 1;
var last_health;
var health_timeout = 10;
var tDates = [], tData = [];
var rightIndex;

if (window.WebSocket) {
	// ����websocket����
    websocket = new WebSocket(
            encodeURI('ws://' + document.domain + ':8888'));
    websocket.onopen = function() {
        console.log('������');
        websocket.send("onlinejack");
        heartbeat_timer = setInterval(function() {
            keepalive(websocket)
        }, 60000);
    };
    websocket.onerror = function() {
        console.log('���ӷ�������');
    };
    websocket.onclose = function() {
        console.log('�Ѿ��Ͽ�����');
        initWs();
    };
    // ��Ϣ����
    websocket.onmessage = function(message) {
        console.log(message)
        console.log(message.data)
        
        if (message.data == "yes") {
        	$(".isRead").text("");
        	console.log($(".isRead"));
        	return;
        }
          
        var jsonMsg = JSON.parse(message.data);
        
        // �޸�δ��
        $.post("UnReadMsg", 
        	{"fromName":jsonMsg.toName, "toName": jsonMsg.fromName}, 
        	function(data) {}
        );
        
        // ֪ͨ�Է��Ѷ�
        $.ajax({
        	url: "IsReadMsg",
        	type: "post",
        	data: {"fromName":jsonMsg.toName, "toName": jsonMsg.fromName, "isRead": "yes"},
        	success: function(data) {
        		//alert("success");
        	},
        	async: true
        });        
        
        // �޸�Ϊ�Ѷ�
        appendMsg(jsonMsg, $("#msgListLeft"));
    };
} else {
    alert("���������֧�����ѡ�<br/>����ʹ�ø߰汾���������<br/>�� IE10����� ���ȸ�  ���ѹ���");
}

var initWs = function() {
    if (window.WebSocket) {
        websocket = new WebSocket(
                encodeURI('ws://' + document.domain + ':8888'));
        websocket.onopen = function() {
            console.log('������');
            websocket.send("onlinejack");
            heartbeat_timer = setInterval(function() {
                keepalive(websocket)
            }, 60000);
        };
        websocket.onerror = function() {
            console.log('���ӷ�������');
        };
        websocket.onclose = function() {
            console.log('�Ѿ��Ͽ�����');
            initWs();
        };
        // ��Ϣ����
        websocket.onmessage = function(message) {
            console.log(message)
            console.log(message.data)
     
            appendMsg(JSON.parse(message.data),$("#msgListLeft"));
        };
    } else {
        alert("���������֧�����ѡ�<br/>����ʹ�ø߰汾���������<br/>�� IE10����� ���ȸ�  ���ѹ���");
    }
}

// ������
function keepalive(ws) {
    var time = new Date();
    if (last_health != -1 && (time.getTime() - last_health > health_timeout)) {
        // ws.close();
    } else {
        if (ws.bufferedAmount == 0) {
            ws.send('connect again from jack...');
        }
    }
}