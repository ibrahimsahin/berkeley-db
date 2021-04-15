package cs.bil342.experiment3.examples.httpserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Converts the received HTTP parameter "str" to uppercase and sends it back to
 * the client.
 * 
 * @author Safa Sofuoglu
 */
public class UpperCaseHandler extends AbstractHandler
{
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);

		try
		{
			response.getWriter().print(request.getParameter("str").toUpperCase());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		response.getWriter().flush();

		baseRequest.setHandled(true);
	}
}
