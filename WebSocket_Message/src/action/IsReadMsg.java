package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.java_websocket.WebSocket;

import websocket.WsPool;
// import websocket.WsServer;

public class IsReadMsg extends HttpServlet {

	/**
	 * @author echo lovely
	 * @date 2020/8/7 8:48
	 * 发送消息后 消息是否已读  
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		// String fromName = req.getParameter("fromName");
		String toName = req.getParameter("toName");
		
		// ajax 发送过来
		String isRead = req.getParameter("isRead");
		
		if ("yes".equals(isRead)) {
			// 对方收到消息
			
			// 根据用户名 拿到 用户与服务器连接的 websocket
			WebSocket wsConn = WsPool.getWsByUser("online" + toName);
			// 通知对方 已经收到消息
			
			System.out.println(toName + "\t" + wsConn);
			WsPool.sendMessageToUser(wsConn, "yes");
		}
		

	}

}
