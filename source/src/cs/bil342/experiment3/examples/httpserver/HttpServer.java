package cs.bil342.experiment3.examples.httpserver;

import org.eclipse.jetty.server.Server;

/**
 * An HTTP server that sends back the content of the received HTTP request in
 * uppercased form. To see this server live, point your web browser to
 * http://localhost:8080/?str=somestring after starting the server.
 * 
 * @author Safa Sofuoglu
 */
public class HttpServer
{
	private static final int DEFAULT_PORT = 8080;

	private int port;

	public HttpServer()
	{
		this.port = DEFAULT_PORT;
	}

	public HttpServer(int port)
	{
		this.port = port;
	}

	public static void main(String[] args)
	{
		new HttpServer().start();
	}

	public void start()
	{
		Server server = new Server(this.port);
		server.setHandler(new UpperCaseHandler());
		try
		{
			server.start();
			server.join();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
