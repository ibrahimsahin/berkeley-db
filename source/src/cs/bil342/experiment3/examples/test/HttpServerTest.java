package cs.bil342.experiment3.examples.test;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.URIUtil;

import cs.bil342.experiment3.examples.httpserver.HttpServer;

/**
 * This JUnit test can be used to test the example HttpServer
 * 
 * @author Safa Sofuoglu
 */
public class HttpServerTest extends TestCase
{
	private static final int PORT = 8080;

	private HttpClient httpClient;

	@Override
	protected void setUp()
	{
		//Start the Http Server:
		new Thread(new Runnable()
		{
			public void run()
			{
				new HttpServer(PORT).start();
			}
		}).start();

		//Start the Http Client:
		httpClient = new HttpClient();
		try
		{
			httpClient.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Test suite()
	{
		return new TestSuite(HttpServerTest.class);
	}

	public void testUpperCase() throws IOException, InterruptedException
	{
		String param = "this is a test";
		
		ContentExchange contentExchange = new ContentExchange();
		contentExchange.setURL("http://localhost:" + PORT);
		contentExchange.setURI("/?str=" + URIUtil.encodePath(param));

		System.out.println("Encoded URI: " + contentExchange.getURI());

		httpClient.send(contentExchange);
		contentExchange.waitForDone();

		assertEquals(200, contentExchange.getResponseStatus()); //HTTP 200 (OK)
		assertEquals(param.toUpperCase(), contentExchange.getResponseContent());
	}

	public void testSymbols() throws IOException, InterruptedException
	{
		String param = "\"<> ?*-=.abc";
		
		ContentExchange contentExchange = new ContentExchange();
		contentExchange.setURL("http://localhost:" + PORT);
		contentExchange.setURI("/?str=" + URIUtil.encodePath(param));

		System.out.println("Encoded URI: " + contentExchange.getURI());

		httpClient.send(contentExchange);
		contentExchange.waitForDone();

		assertEquals(200, contentExchange.getResponseStatus()); //HTTP 200 (OK)
		assertEquals(param.toUpperCase(), contentExchange.getResponseContent());
	}
}
