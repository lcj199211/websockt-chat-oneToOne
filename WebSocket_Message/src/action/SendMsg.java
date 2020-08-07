package action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.java_websocket.WebSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import websocket.WsPool;
import dao.MessageDao;
import entity.Message;

public class SendMsg extends HttpServlet {

	/**
	 * ʹ��websocket ��ͻ��˷�����Ϣ
	 */
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		MessageDao dao = new MessageDao();
		String fromName = req.getParameter("fromName");
		String toName = req.getParameter("toName");
		String msgContent = req.getParameter("msgContent");

		Message msg = new Message();
		msg.setFromName(fromName);
		msg.setToName(toName);
		msg.setMsgContent(msgContent);
		
		int count = dao.sendMsg(msg);
		
		if (count > 0) {
			// websocket��ͻ��˷�����Ϣ
			// �����û����ҵ� �û��ͷ��������ӵ���websocket����
			WebSocket wsConn = WsPool.getWsByUser("online" + toName);
			// ���͵�����Ϣ
			msg.setMsgDate(new Timestamp(System.currentTimeMillis()));
			// System.out.println(wsConn + "\t" + JSON.toJSONString(msg));
			WsPool.sendMessageToUser(wsConn, JSON.toJSONString(msg));		
			
		}
		
		// �����ɹ� ����ʱ��
		if (count > 0) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("count", count);
			DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			jsonObj.put("date", sf.format(new Date()));
			
			resp.getWriter().print(jsonObj.toJSONString()); // json: type -> string...
		}
		
	}

}
