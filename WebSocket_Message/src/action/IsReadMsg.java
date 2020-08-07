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
	 * ������Ϣ�� ��Ϣ�Ƿ��Ѷ�  
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		// String fromName = req.getParameter("fromName");
		String toName = req.getParameter("toName");
		
		// ajax ���͹���
		String isRead = req.getParameter("isRead");
		
		if ("yes".equals(isRead)) {
			// �Է��յ���Ϣ
			
			// �����û��� �õ� �û�����������ӵ� websocket
			WebSocket wsConn = WsPool.getWsByUser("online" + toName);
			// ֪ͨ�Է� �Ѿ��յ���Ϣ
			
			System.out.println(toName + "\t" + wsConn);
			WsPool.sendMessageToUser(wsConn, "yes");
		}
		

	}

}
