package bioskopp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosko.DAO.KorisnikDAO;
import bioskop.model.Korisnik;
import bioskop.model.Uloga;


@SuppressWarnings("serial")
public class RegistracijaServlet extends HttpServlet {
	
	/*
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String korisnickoIme = request.getParameter("korisnickoIme");
			if (KorisnikDAO.get(korisnickoIme) != null)
				throw new Exception("Korisnicko ime vec postoji!");
			if ("".equals(korisnickoIme))
				throw new Exception("Korisnicko ime je prazno!");

			String lozinka = request.getParameter("lozinka");
			if ("".equals(lozinka))
				throw new Exception("Lozinka je prazna!");
			
			Date trenutno = new java.util.Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			String datumRegistracije = dateFormat.format(trenutno);
			
			Korisnik korisnik = new Korisnik(korisnickoIme, lozinka, Uloga.KORISNIK);
			KorisnikDAO.dodajKorisnika(korisnik);

			request.getRequestDispatcher("./UspjesnoServlet").forward(request, response);
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Nepredvidjena greska!";
				ex.printStackTrace();
			}

			Map<String, Object> data = new LinkedHashMap<>();
			data.put("message", message);

			request.setAttribute("data", data);
			request.getRequestDispatcher("./GreskaServlet").forward(request, response);
		}
	}
*/
}
