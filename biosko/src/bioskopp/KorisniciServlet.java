package bioskopp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosko.DAO.KorisnikDAO;
import bioskop.model.Korisnik;
import bioskop.model.Uloga;

/**
 * Servlet implementation class KorisniciServlet
 */
@SuppressWarnings("serial")
public class KorisniciServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ulogovanKorisnikIme = (String) request.getSession().getAttribute("ulogovanKorisnikIme");
		if (ulogovanKorisnikIme == null) {
			request.getRequestDispatcher("./OdjavaServlet").forward(request, response);
			return;
		}
		try {
			Korisnik ulogovaniKorisnik = KorisnikDAO.get(ulogovanKorisnikIme);
			if (ulogovaniKorisnik == null) {
				request.getRequestDispatcher("./OdjavaServlet").forward(request, response);
				return;
			}
			String korisnickoIme = request.getParameter("korisnickoImeF"); 
			korisnickoIme = (korisnickoIme != null? korisnickoIme : "");
			Uloga uloga = Uloga.valueOf(request.getParameter("uloga"));
			uloga = (uloga != null? uloga : Uloga.Korisnik); //da dobijem listu obicnih
			
			List <Korisnik> filtriraniKorisnici = null; 
			try {
					filtriraniKorisnici = KorisnikDAO.getAll(korisnickoIme, uloga);
				}
				catch(Exception e) {e.printStackTrace();}
		
		Map<String,Object> data = new LinkedHashMap<String,Object>();
		data.put("filtriraniKorisnici", filtriraniKorisnici);
		request.setAttribute("data", data);
		request.getRequestDispatcher("./SuccessServlet").forward(request, response); 
		}catch(Exception e) {e.printStackTrace();}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
