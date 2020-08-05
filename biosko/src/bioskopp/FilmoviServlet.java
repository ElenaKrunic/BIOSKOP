package bioskopp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.json.simple.JSONObject;

import biosko.DAO.FilmDAO;
import biosko.DAO.KorisnikDAO;
import bioskop.model.Film;
import bioskop.model.Korisnik;


public class FilmoviServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter(); 
		String action = request.getParameter("action"); 
		String filmId = request.getParameter("filmId"); 
		
		switch(action) {
		case "loadMovies" : 
			out.print(ucitajFilmove());
			break; 
			
		case "loadGenres" : 
			out.print(ucitajZanrove());
			break;
			
		case "filter" : 
			out.print(filterFilm(request));
			break; 
			
		case "ucitajFilm" : 
			out.print(ucitajJedanFilm(filmId));
			break;
		
			
		default: break;
		}
	}
	/*
	private JSONObject load1Movie(String filmId) {
		JSONObject response = new JSONObject(); 
		boolean status = false; 
		JSONObject film = null; 
		try {
			System.out.println("ID filma je " + filmId); 
			film = FilmDAO.getById(filmId); 
			if(film!=null) {
				status = true; 
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		response.put("status", status); 
		response.put("film", film); 
		return response;
	}
	*/

	private JSONObject ucitajJedanFilm(String id) {
		// TODO Auto-generated method stub
		JSONObject response = new JSONObject(); 
		JSONObject film = null; 
		boolean status = false; 
		
		try {
			film = FilmDAO.getById(id);
			System.out.println("Film je " + film); 
			System.out.println("ID filma je " + id); 
			status = true; 
		} catch(Exception e) {
			e.printStackTrace();
		}
		response.put("film",film); 
		response.put("status",status); 
		return response; 
	}

	private JSONObject filterFilm(HttpServletRequest request) {
		JSONObject response = new JSONObject();

		String naziv = request.getParameter("naziv"); 
		int trajanje = 0; 
		try{
			trajanje = Integer.valueOf(request.getParameter("trajanje"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		String zanrovi = request.getParameter("zanr"); 
		String opis = request.getParameter("opis"); 
		String glumci = request.getParameter("glumci"); 
		String reziser = request.getParameter("reziser"); 
		String godina = request.getParameter("godina"); 
		String distributer = request.getParameter("distributer");
		String zemljaPorijekla = request.getParameter("zemlja"); 
		
		ArrayList<JSONObject> filmovi = new ArrayList<JSONObject>(); 
		try {
			filmovi = FilmDAO.getMovies(naziv, trajanje, zanrovi, opis, glumci, reziser, godina, distributer, zemljaPorijekla); 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		response.put("filmovi",filmovi); 
		return response;
	}

	private JSONObject ucitajFilmove() {
		Boolean status = false; 
		ArrayList<JSONObject> filmovi = new ArrayList<JSONObject>(); 
		JSONObject response = new JSONObject(); 
		try {
			filmovi = FilmDAO.getMovies("", 0, "","","","","","","");
			if(filmovi.size() > 0) {
				status = true; 
			}
		} catch(Exception e) {
			System.out.println("Ucitaj sve"); 
			e.printStackTrace(); 
		}
		
		response.put("status", status); 
		response.put("filmovi", filmovi);
		return response;
	}
	
	private JSONObject ucitajZanrove() {
		boolean status = false; 
		JSONObject response = new JSONObject(); 
		ArrayList<String> listaZanrova = FilmDAO.getGenres(); 
		if(listaZanrova.size() > 0) {
			status = true; 
		}
		response.put("status", status); 
		response.put("zanrovi", listaZanrova); 
		//System.out.println("Zanrovi su:  " + listaZanrova); 
		return response;
	}
	
	/*

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
			
			List<Film> filtriraniFilmovi;
			try {
				filtriraniFilmovi = FilmDAO.getAll(naziv, zanr,najkraceTraje,najduzeTraje,distributer, zemljaPorijekla, najranijaGodina, posljednjaGodina);
				Map<String,Object> data = new LinkedHashMap<>(); 
				data.put("filtriraniFilmovi", filtriraniFilmovi); 
				request.setAttribute("data", data);
				request.getRequestDispatcher("/.UspjesnoServlet").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//regulisi ovde 
		} catch(Exception e) {e.printStackTrace();} 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
*/
}
