package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MessageDao;
import entity.Message;

public class UpdAllMsgStatus extends HttpServlet {

	/**
	 * 修改发给我的消息 为 已读
	 */
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		MessageDao dao = new MessageDao();
		
		Message msg = new Message();
		msg.setFromName(req.getParameter("fromName"));
		msg.setToName(req.getParameter("toName"));
		dao.updMsgStatusInLoad(msg);
	}
	
}
