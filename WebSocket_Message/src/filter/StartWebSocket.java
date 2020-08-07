package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.java_websocket.WebSocketImpl;

import websocket.WsServer;

public class StartWebSocket implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.startWebSocketInstantMsg();
	}
	
	/**
	 * ����websocket ����
	 */
	public void startWebSocketInstantMsg() {
		System.out.println("��ʼ����webSocket����...");
		WebSocketImpl.DEBUG = false;
		// �˿�
		WsServer server = new WsServer(8888);
		server.start();
	}

}
