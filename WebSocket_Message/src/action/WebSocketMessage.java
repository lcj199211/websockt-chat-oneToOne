package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import dao.MessageDao;
import entity.Message;

public class WebSocketMessage extends HttpServlet {

	/**
	 * message...
	 */
	private static final long serialVersionUID = 1L;
	
	private MessageDao dao = new MessageDao();
	
	// 历史消息
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		String fromName = req.getParameter("fromName");
		String toName = req.getParameter("toName");
		
		Message msg = new Message();
		msg.setFromName(fromName);
		msg.setToName(toName);
		// 历史 已读消息！！！
		List<Message> list = dao.hasReadMsg(msg);
		
		String msgList = JSON.toJSONString(list);

		resp.setContentType("text/json;charset=utf-8");
		resp.getWriter().print(msgList);
		
	}
	
	
}
