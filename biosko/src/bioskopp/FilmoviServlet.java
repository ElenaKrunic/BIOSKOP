package bioskopp;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosko.DAO.FilmDAO;
import biosko.DAO.KorisnikDAO;
import bioskop.model.Film;
import bioskop.model.Korisnik;


public class FilmoviServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			//uzimam svaki filter i provjeravam
			String naziv = request.getParameter("nazivFilter");
			naziv = (naziv != null? naziv:"" );
			
			String zanr = (String) request.getAttribute("zanr");
			zanr = (zanr != null? zanr:"");
			
			int najkraceTraje = 0;
			try {
				String najmanjaMinutazaFilter = request.getParameter("najmanjaMinutazaFilter");
				najkraceTraje = Integer.parseInt(najmanjaMinutazaFilter);
				najkraceTraje = (najkraceTraje > 0? najkraceTraje: 0);
			} catch(Exception ex) {}
				
			int najduzeTraje = Integer.MAX_VALUE;
			try {
				String najvecaMinutazaFilter = request.getParameter("najvecaMinutazaFilter");
				najduzeTraje = Integer.parseInt(najvecaMinutazaFilter);
				najduzeTraje = (najduzeTraje > 0? najduzeTraje : 0);
			} catch(Exception e) {e.printStackTrace();}
			
			String distributer = request.getParameter("distributerFilter");
			distributer = (distributer != null? distributer:"");
			
			String zemljaPorijekla = request.getParameter("zemljaPorijeklaFilter");
			zemljaPorijekla = (zemljaPorijekla != null? zemljaPorijekla:"");
			
			int najranijaGodina = 1900;
			try {
				String najranijaGodinaFilter = request.getParameter("najranijaGodinaFilter");
				najranijaGodina = Integer.parseInt(najranijaGodinaFilter);
				najranijaGodina = (najranijaGodina > 0? najranijaGodina:0);
			} catch(Exception ex) {}
			
			int posljednjaGodina = 2020;
			try {
				String posljednjaGodinaFilter = request.getParameter("posljednjaGodinaFilter");
				posljednjaGodina = Integer.parseInt(posljednjaGodinaFilter);
				posljednjaGodina = (posljednjaGodina > 0? posljednjaGodina:0);
			} catch(Exception ex) {ex.printStackTrace();}
			
			//pravim listu filtriranih filmova i pozivam dao sloj te dao sloju prosljedjujem sve elemente filtriranog filma 
			List<Film> filtriraniFilmovi = FilmDAO.getAll(naziv, zanr,najkraceTraje,najduzeTraje,distributer, zemljaPorijekla, najranijaGodina, posljednjaGodina);
			Map<String,Object> data = new LinkedHashMap<>(); 
			data.put("filtriraniFilmovi", filtriraniFilmovi); 
			request.setAttribute("data", data);
			request.getRequestDispatcher("/.UspjesnoServlet").forward(request, response);
		} catch(Exception e) {e.printStackTrace();} 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
