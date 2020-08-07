package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MessageDao;
import entity.Message;

public class UnReadMsg extends HttpServlet {

	/**
	 * δ����Ϣ �޸�Ϊ�Ѷ�
	 */
	private static final long serialVersionUID = 1L;
	
	private MessageDao dao = new MessageDao();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String fromName = req.getParameter("fromName");
		String toName = req.getParameter("toName");
		
		Message msg = new Message();
		msg.setFromName(fromName);
		msg.setToName(toName);

		// �޸���Ϣ�Ѷ� �Է������ҵ�
		int count = dao.updMsgStatus(msg);

		resp.getWriter().print(count);
	
	}

}
