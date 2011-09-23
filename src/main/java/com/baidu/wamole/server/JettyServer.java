package com.baidu.wamole.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

public class JettyServer {
	private static Server server;
	private static int port = 8080;

	private JettyServer() {
		if (null == server) {
			server = new Server(JettyServer.port);
		}
	}

	private static Server getJettyServer() {
		if (null == server) {
			new JettyServer();
		}
		return server;
	}

	public static void setPort(int port) {
		JettyServer.port = port;
		new JettyServer();
	}

	public static void setHandler(Handler handler) {
		server.setHandler(handler);
	}

	public static void start() throws Exception {
		getJettyServer();
		server.start();
		server.join();
	}
}
