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
import biosko.DAO.SalaDAO;
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
			
			/*
		case "filter" : 
			out.print(filterFilm(request));
			break; */
			
		case "ucitajFilm" : 
			out.print(ucitajJedanFilm(filmId));
			break;
			
		case "filtrirajFilm":
			out.print(filtrirajFilm(request)); 
			 break;
		
		case "editMovie" : 
			out.print(editMovie(request));
			break;
			
		case "addMovie": 
			out.print(addMovie(request)); 
			break;
			
		case "deleteMovie": 
			out.print(deleteMovie(filmId));
			break;
			
		default: break;
		}
	}

	private JSONObject deleteMovie(String filmId) {
		boolean status = FilmDAO.deleteMovie(filmId);
    	JSONObject response = new JSONObject();

	    response.put("status", status);
	    
	    return response;
	}


	private JSONObject addMovie(HttpServletRequest request) {
    	JSONObject response = new JSONObject();
		boolean status = FilmDAO.addMovie(request);
    	response.put("status", status);
    	//System.out.println("Film dodat " + status); 
	    return response;
	}

	private JSONObject editMovie(HttpServletRequest request) {
		JSONObject response = new JSONObject(); 
		response = FilmDAO.editMovie(request); 
		return response;
	}

	private JSONObject filtrirajFilm(HttpServletRequest request) {
		String naziv = request.getParameter("naziv");
    	int trajanje = 0;
    	try {
    		trajanje = Integer.valueOf(request.getParameter("trajanje"));
    	}
    	catch(Exception e) {

    	}
    	String zanrovi = request.getParameter("zanr");
    	String opis = request.getParameter("opis");
    	String glumci = request.getParameter("glumci");
    	String reziser = request.getParameter("reziser");
    	String godina = request.getParameter("godina");
    	String distributer = request.getParameter("distributer");
    	String zemlja = request.getParameter("zemlja");
    	Boolean status = false;
    	ArrayList<JSONObject> filmovi = new ArrayList<JSONObject>();
    	try {
    		//String naziv1,int trajanje1,String zanrovi1,String opis1,String glumci1,String reziser1,String godina1,String distributer1,String zemlja1
    		filmovi = FilmDAO.getMovies(naziv,trajanje,zanrovi,opis,glumci,reziser,godina,distributer,zemlja);
    		if(filmovi.size()>0) {
    			status = true;
    		}

    	}
    	catch (Exception e) {
    		System.out.println("Puklo je ovde na ucitaj sve filmove.");
    	}
    	JSONObject odg = new JSONObject();

	    odg.put("status", status);
	    odg.put("odredjeniFilmovi", filmovi);

	    return odg;
	}

	private JSONObject ucitajJedanFilm(String id) {
		// TODO Auto-generated method stub
		JSONObject response = new JSONObject(); 
		JSONObject film = null; 
		boolean status = false; 
		
		try {
			film = FilmDAO.getById(id);
			//System.out.println("Film je " + film); 
			//System.out.println("ID filma je " + id); 
			status = true; 
		} catch(Exception e) {
			e.printStackTrace();
		}
		response.put("film",film); 
		response.put("status",status); 
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
				System.out.println("Filmovi su: \n" + filmovi); 
			}
		} catch(Exception e) { 
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
}
