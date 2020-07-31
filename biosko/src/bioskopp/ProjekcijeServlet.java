package bioskopp;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import biosko.DAO.FilmDAO;
import biosko.DAO.ProjekcijeDAO;
import biosko.DAO.SalaDAO;
import bioskop.model.Film;
import bioskop.model.Projekcija;
import bioskop.model.Sala;

@SuppressWarnings("serial")
public class ProjekcijeServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter ispis = response.getWriter();
		String action = request.getParameter("action"); 
		
		if(action != null && request != null) {
			switch(action) {
			case "projekcijeZaDanas" :
				ispis.print(ucitajProjekcijeZaDanasMetoda(request));
				break; 
			}
		}
	}
	
	   private JSONObject ucitajProjekcijeZaDanasMetoda(HttpServletRequest request) {
	    	JSONObject obj = new JSONObject();
	    	boolean status = false;
	    	ArrayList<JSONObject> listaProjekcija = new ArrayList<JSONObject>();
	    	
	    	Film film = null;
	    	Sala sala = null;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = new Date();
			String sredjenDatum = dateFormat.format(date);
	    	ArrayList<Projekcija> lista = ProjekcijeDAO.ucitajProjekcijuZaDanasnjiDatum(request,sredjenDatum);
	    	for (Projekcija projekcija : lista) {
				try {
					film = FilmDAO.getFilmObjectById(projekcija.getFilmId());
					sala = SalaDAO.getSalaObjectById(projekcija.getSalaId());
					JSONObject projekcijaJson = new JSONObject();
					if(film!=null && sala!=null) {
						projekcijaJson.put("id_projekcije", projekcija.getId());
						projekcijaJson.put("id_filma", film.getId());
						projekcijaJson.put("naziv_filma", film.getNaziv());
						String datumProj = dateFormat1.format(projekcija.getDatumPrikazivanje());
						projekcijaJson.put("terminProjekcije", datumProj);
						projekcijaJson.put("tipProjekcije", projekcija.getTipProjekcije());
						projekcijaJson.put("id_sale", sala.getId());
						projekcijaJson.put("nazivSale", sala.getNaziv());
						projekcijaJson.put("cijena", projekcija.getCijenaKarte());
					}
					listaProjekcija.add(projekcijaJson);
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    	obj.put("status", status);
	    	obj.put("listaProjekcija", listaProjekcija);
	    	return obj;
	    }
	/*
	private JSONObject projekcijeZaDanasMetoda(HttpServletRequest request) {
		JSONObject projekcijaObj = new JSONObject(); 
		ArrayList<JSONObject> listaProjekcijaObj = new ArrayList<JSONObject>();
		Film film = null; 
		Sala sala = null; 
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		String danasnjiDatum = df1.format(date);
		ArrayList<Projekcija> obicnaListaProjekcija = ProjekcijeDAO.ucitajProjekcijuZaDanasnjiDatum(request, danasnjiDatum);
		for(Projekcija obicnaProjekcija : obicnaListaProjekcija) {
			try {
				film = FilmDAO.getFilmObjectById(obicnaProjekcija.getFilmId()); 
				sala = SalaDAO.getSalaObjectById(obicnaProjekcija.getSalaId());
				JSONObject jsonProjekcija = new JSONObject(); 
				if(film!=null && sala !=null) {
					jsonProjekcija.put("idProjekcije", obicnaProjekcija.getId()); 
					jsonProjekcija.put("idFilma", film.getId()); 
					jsonProjekcija.put("nazivFilma", film.getNaziv()); 
					String d = df2.format(obicnaProjekcija.getDatumPrikazivanje()); 
					jsonProjekcija.put("terminProjekcije", d); 
					jsonProjekcija.put("tipProjekcije", obicnaProjekcija.getTipProjekcije());
					jsonProjekcija.put("idS", sala.getId()); 
					jsonProjekcija.put("nazivSale", sala.getNaziv());
					jsonProjekcija.put("cijenaK",obicnaProjekcija.getCijenaKarte()); 
				}
				listaProjekcijaObj.add(jsonProjekcija);
			} catch(Exception e) {
				System.out.println("Ne postoji projekcija u listi"); 
			}
		}
		projekcijaObj.put("listaProjekcijaObj",listaProjekcijaObj); 
		return projekcijaObj; 
	}
	*/
	
}
	/*
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter(); 
		String action = request.getParameter("action"); 
		
		if(action != null && request != null) {
			switch(action) {
			case "danasProjekcije": 
				out.print(ucitajProjekcijeZaDanas(request));
				break;
			}
		}
	}

	private JSONObject ucitajProjekcijeZaDanas(HttpServletRequest request) {
    	JSONObject obj = new JSONObject();
    	boolean status = false;
    	ArrayList<JSONObject> listaProjekcija = new ArrayList<JSONObject>();
    	
    	Film film = null;
    	Sala sala = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		String sredjenDatum = dateFormat.format(date);
    	ArrayList<Projekcija> lista = ProjekcijeDAO.ucitajZaDatum(request,sredjenDatum);
    	for (Projekcija projekcija : lista) {
			try {
				film = FilmDAO.getFilmObjectById(projekcija.getFilmId());
				sala = SalaDAO.getSalaObjectById(projekcija.getSalaId());
				JSONObject t_p = new JSONObject();
				if(film!=null && sala!=null) {
					t_p.put("id_projekcije", projekcija.getId());
					t_p.put("id_filma", film.getId());
					t_p.put("naziv_filma", film.getNaziv());
					String datumProj = dateFormat1.format(projekcija.getDatumPrikazivanje());
					t_p.put("terminProjekcije", datumProj);
					t_p.put("tip_projekcije", projekcija.getTipProjekcije());
					t_p.put("id_sale", sala.getId());
					t_p.put("naziv_sale", sala.getNaziv());
					t_p.put("cena", projekcija.getCijenaKarte());
				}
				listaProjekcija.add(t_p);
				status = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	obj.put("status", status);
    	obj.put("listaProjekcija", listaProjekcija);
    	return obj;
    }
}
	*/