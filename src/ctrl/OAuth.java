package ctrl;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Brain;

/**
 * Servlet implementation class OAuth
 */
@WebServlet("/OAuth.do")
public class OAuth extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OAuth()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		if (request.getParameter("calc") == null && request.getParameter("user") == null)
		{
			this.getServletContext().getRequestDispatcher("/OAuth.html").forward(request, response);
		} else
		{
			
			try
			{
				if (request.getParameter("calc") != null)
				{
					//CtrlUtil.punch_request(request, this, "Oauth");
					String url = "https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi"+
								"?back="+request.getRequestURL();
					response.sendRedirect(url);
				//	System.out.println(request.getRequestURL().toString());
				}
				else if (request.getParameter("user") != null)
				{
					Writer out = response.getWriter();
					response.setContentType("text/html");
					StringBuffer html = new StringBuffer();
					html.append("<html><body>");
					html.append("<p><a href='Dash.do'>Back to Dashboard</a></p>");
					html.append("<code>"+request.getQueryString()+"</code>");
					html.append("</body></html>");
					out.write(html.toString());
				//	System.out.println(request.getQueryString().toString());
				}
			} catch (Exception e)
			{
				response.setContentType("text/html");
				Writer out = response.getWriter();
				String html = "<html><body>";
				html += "<p><a href=' Dash.do'>Back to Dashboard</a></p>";
				html += "<p>Error " + e.getMessage() + "</p>";
				out.write(html);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
