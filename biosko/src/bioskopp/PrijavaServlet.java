package bioskopp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosko.DAO.KorisnikDAO;
import bioskop.model.Korisnik;


@SuppressWarnings("serial")
public class PrijavaServlet extends HttpServlet {
	
	/*
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		try {
			Korisnik korisnik = KorisnikDAO.get(userName,password);
			
			if(korisnik == null) {
				request.getRequestDispatcher("/.GreskaServlet").forward(request, response);
				return;
			}
			
			request.getSession().setAttribute("ulogovanKorisnik", korisnik.getKorisnickoIme());
			request.getRequestDispatcher("/.UspjesnoServlet").forward(request, response);
			
		}
		catch(Exception ex) {ex.printStackTrace();}
		
	}

*/
}
