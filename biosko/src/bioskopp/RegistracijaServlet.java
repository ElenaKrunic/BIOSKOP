package bioskopp;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosko.DAO.KorisnikDAO;
import bioskop.model.Korisnik;
import bioskop.model.Uloga;


/**
 * Servlet implementation class RegistracijaServlet
 */
public class RegistracijaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String userName = request.getParameter("userName");
			if (KorisnikDAO.get(userName) != null)
				throw new Exception("Korisnicko ime vec postoji!");
			if ("".equals(userName))
				throw new Exception("Korisnicko ime je prazno!");

			String password = request.getParameter("password");
			if ("".equals(password))
				throw new Exception("Lozinka je prazna!");
			
			Korisnik korisnik = new Korisnik(userName, password, Uloga.Korisnik);
			KorisnikDAO.dodajKorisnika(korisnik);

//			response.sendRedirect("./Login.html");
			request.getRequestDispatcher("./UspjesnoServlet").forward(request, response);
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Nepredvidjena greska!";
				ex.printStackTrace();
			}

//			request.setAttribute("message", message);
//			request.getRequestDispatcher("./Message.jsp").forward(request, response);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("message", message);

			request.setAttribute("data", data);
			request.getRequestDispatcher("./GreskaServlet").forward(request, response);
		}
	}

}
