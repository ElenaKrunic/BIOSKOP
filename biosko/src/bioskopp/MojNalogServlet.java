package bioskopp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MojNalogServlet
 */
public class MojNalogServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String korisnickoIme = request.getParameter("korisnickoIme");
		String sifra = request.getParameter("sifra");
		String uloga = request.getParameter("uloga");
		
		System.out.println(korisnickoIme);
		System.out.println(sifra);
		System.out.println(uloga);
		
		response.getWriter().println("success");
	}

}
