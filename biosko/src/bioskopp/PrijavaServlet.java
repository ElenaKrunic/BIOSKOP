package bioskopp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosko.DAO.KorisnikDAO;
import bioskop.model.Korisnik;


public class PrijavaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		try {
			Korisnik korisnik = KorisnikDAO.get(userName,password);
			
			if(userName == null) {
				request.getRequestDispatcher("/.OdjavaServlet").forward(request, response);
				return;
			}
			
			//sesiju logovanom korisniku 
			request.getSession().setAttribute("ulogovanKorisnik", korisnik.getKorisnickoIme());
			request.getRequestDispatcher("/.UspjesnoServlet").forward(request, response);
			
		}
		catch(Exception ex) {ex.printStackTrace();}
		
	}

}
