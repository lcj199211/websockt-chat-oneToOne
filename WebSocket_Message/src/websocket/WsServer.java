package websocket;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WsServer extends WebSocketServer {
    public WsServer(int port) {
        super(new InetSocketAddress(port));
    }

    public WsServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // ws连接的时候触发的代码，onOpen中我们不做任何操作

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        //断开连接时候触发代码
        userLeave(conn);
        System.out.println(reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
    	//客户端发送消息给服务器，自动执行此方法
        System.out.println(message);
        
        //服务器端发送消息给客户端，
        //conn.send("消息内容");
        if(null != message &&message.startsWith("online")){
            userJoin(conn,message);//用户加入
        }else if(null != message && message.startsWith("offline")){
            userLeave(conn);
        }

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //错误时候触发的代码
        System.out.println("on error");
        ex.printStackTrace();
    }
    /**
     * 去除掉失效的websocket链接
     * @param conn
     */
    private void userLeave(WebSocket conn){
        WsPool.removeUser(conn);
    }
    /**
     * 将websocket加入用户池
     * @param conn
     * @param userName
     */
    private void userJoin(WebSocket conn,String userName){
        WsPool.addUser(userName, conn);
    }

}
