package bioskopp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import biosko.DAO.FilmDAO;
import biosko.DAO.ProjekcijeDAO;
import biosko.DAO.SalaDAO;
import biosko.DAO.TipProjekcijeDAO;
import bioskop.model.Film;
import bioskop.model.Projekcija;
import bioskop.model.Sala;
import bioskop.model.TipProjekcije;

@SuppressWarnings("serial")
public class ProjekcijeServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter ispis = response.getWriter();
		String action = request.getParameter("action"); 
		String projekcijaId = request.getParameter("projekcijaId"); 
		
		if(action != null && request != null) {
			switch(action) {
			case "projekcijeZaDanas" :
				ispis.print(ucitajProjekcijeZaDanasMetoda(request));
				break; 
				
			case "loadProjection" : 
				ispis.print(ucitajJednuProjekciju(request)); 
				break;
				
			case "filtrirajProjekciju" : 
				ispis.print(filtriranjeProjekcije(request));
				break;
				
			case "ucitajTipoveProjekcija":
				ispis.print(ucitajTipoveProjekcija()); 
				break; 
				
			case "ucitajFilmProjekcija":
				ispis.print(ucitajFilm(request));
				break;
				
			case "dodajProjekciju" : 
				ispis.print(dodajProjekciju(request));
				break;
				
			case "ucitajSale": 
				ispis.print(ucitajSale()); 
				break;
				
			case "ucitajSaleSaTipovimaProjekcija" :
				ispis.print(ucitajSaluProjekcija(request));
				break; 
				
			case "ucitajSaluProjekcija":
				ispis.print(ucitajSaluProjekcija(request)); 
				break;
				
			case "obrisiProjekciju" :
				ispis.print(obrisiProjekciju(projekcijaId));
				break;
			}
		}
	}
		private JSONObject obrisiProjekciju(String projekcijaId) {
		JSONObject response = new JSONObject();
		boolean status = ProjekcijeDAO.obrisiProjekciju(projekcijaId); 
		
		response.put("status",status);
		
		return response;
	}

		private JSONObject dodajProjekciju(HttpServletRequest request) {
		JSONObject response = new JSONObject(); 
		boolean status = false; 

		String filmID = request.getParameter("projekcijaId"); 
		String pocetakProjekcije = request.getParameter("pocetakProjekcije"); 
		String idSale = request.getParameter("salaProjekcije"); 
		String idTipProjekcije = request.getParameter("tipProjekcije"); 
		String cijenaProjekcije = request.getParameter("cijenaProjekcije"); 
		String administrator = (String)request.getSession().getAttribute("userName");
		
		//System.out.println("Ovo je idSale parametar u servletu " +  idSale); 
		try {
			Film film = FilmDAO.getFilmObjectById(Integer.valueOf(filmID)); 
					
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    		Date pt = format.parse(pocetakProjekcije);	
    		Date ddatum = format.parse(pocetakProjekcije);
			Calendar c = Calendar.getInstance();
			c.setTime(ddatum);
			c.add(Calendar.MINUTE, film.getTrajanje());
			ddatum = c.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");       
			String krajTermina = df.format(ddatum);
			
			//ovo
			/*
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			Date pp = format.parse(pocetakProjekcije);
			Calendar calendar = Calendar.getInstance(); 
			Date datum = format.parse(pocetakProjekcije);
			calendar.setTime(datum);
			calendar.add(Calendar.MINUTE, film.getTrajanje());
			datum = calendar.getTime(); 
			DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			String krajTermina = format2.format(datum); */
			
			TipProjekcije tipProjekcije = TipProjekcijeDAO.getTipProjekcijeObjectById(Integer.valueOf(idTipProjekcije));
			Projekcija projekcija = new Projekcija(1, Integer.valueOf(filmID), tipProjekcije.getNaziv(), Integer.valueOf(idSale), pt, Double.valueOf(cijenaProjekcije), administrator, "Active", SalaDAO.maksimalnoSjedistaSale(idSale), 0);
			status = ProjekcijeDAO.dodajProjekciju(projekcija, krajTermina); 
			System.out.println("Status u servletu " + status); 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		response.put("status",status); 
		return response;
	}

		private JSONObject ucitajSaluProjekcija(HttpServletRequest request) {
			 JSONObject response = new JSONObject();
			   boolean status = false;
			   
			   try {
				   ArrayList<JSONObject> sale = SalaDAO.getSaleSaProjekcijama();
				  // ArrayList<JSONObject> filmovi = FilmDAO.getMovies("", 0, "", "", "", "", "", "", "");
				   response.put("sale", sale);
				   status = true;
			   }catch(Exception e) {
				   e.printStackTrace();
			   }
			   
			   response.put("status", status);
			   return response;
	}

		private JSONObject ucitajFilm(HttpServletRequest request)  {
			 JSONObject odg = new JSONObject();
			   boolean status = false;
			   
			   try {
				   ArrayList<JSONObject> filmovi = FilmDAO.getMovies("", 0, "", "", "", "", "", "", "");
				   odg.put("filmovi", filmovi);
				   status = true;
			   }catch(Exception e) {
				   e.printStackTrace();
			   }
			   
			   odg.put("status", status);
			   return odg;
	}

		private JSONObject ucitajSale() {
		JSONObject response = new JSONObject(); 
		boolean status = false; 
		ArrayList<String> listaSala = SalaDAO.getSale();
		if(listaSala.size() > 0) {
			status = true; 
		}
		response.put("listaSala", listaSala);
		response.put("status",status);  
		return response;
	}

		private JSONObject ucitajTipoveProjekcija() {
		boolean status = false; 
		JSONObject response = new JSONObject(); 
		ArrayList<String> listaTipovaProjekcija = ProjekcijeDAO.getTipoviProjekcija(); 
		//System.out.println(listaTipovaProjekcija);
		if(listaTipovaProjekcija.size() > 0) {
			status = true; 
		}
		
		response.put("status", status); 
		response.put("listaTipovaProjekcija", listaTipovaProjekcija); 
		//System.out.println("Tipovi projekcija su : " + listaTipovaProjekcija); 
		return response;
	}

		private JSONObject filtriranjeProjekcije(HttpServletRequest request)  {
			JSONObject odg = new JSONObject();
	    	boolean status = false;
	    	String message = "Unexpected error";
	    	ArrayList<JSONObject> lista = new ArrayList<JSONObject>();
	    	String id_Filma = request.getParameter("filmId");
	    	String pocetak = request.getParameter("pocetakProjekcije");
	    	String pocetakKraj = request.getParameter("krajProjekcije");
	    	String idSale = request.getParameter("idSala");
	    	String oznakaTipa = request.getParameter("tipProjekcije");
	    	String cenaMin = request.getParameter("cijenaMin");
	    	String cenaMax = request.getParameter("cijenaMax");
	    	try {
	        	if(id_Filma==null || id_Filma=="") {
	        		id_Filma  = "%%";
	        	}
	        	if(idSale==null || idSale=="") {
	        		idSale  = "%%";
	        	}
	        	if(oznakaTipa==null || oznakaTipa=="") {
	        		oznakaTipa  = "%%";
	        	}
	        	if(!(Integer.valueOf(cenaMin)>0)) {
	        		cenaMin = String.valueOf(0);
	        	}
	        	if(!(Integer.valueOf(cenaMax)>0)) {
	        		cenaMax = String.valueOf(0);
	        	}
	        	if(Integer.valueOf(cenaMin)>Integer.valueOf(cenaMax)) {
	        		cenaMax = cenaMin;
	        	}
	        	if(pocetak.length()!=16) {
	        		pocetak = "2000-12-31 10:10";
	        	}
	        	if(pocetakKraj.length()!=16) {
	        		pocetakKraj = "2100-12-31 14:02";
	        	}
	        	System.out.println("ID FILMA : "+id_Filma);
	    		JSONObject srv = ProjekcijeDAO.filterProjekcije(id_Filma, pocetak, pocetakKraj, idSale, oznakaTipa, cenaMin, cenaMax);
	    		if((Boolean) srv.get("status")) {
	    			ArrayList<Projekcija> l = (ArrayList<Projekcija>) srv.get("listaProjekcija");
	    			for (Projekcija projekcija : l) {
						JSONObject pr = new JSONObject();
						pr.put("ID",projekcija.getId());
						pr.put("ID_Filma",projekcija.getFilmId());
						pr.put("Naziv_Filma",FilmDAO.getFilmObjectById(projekcija.getFilmId()).getNaziv());
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						String datum = df.format(projekcija.getDatumPrikazivanje());
						pr.put("Termin",datum);
						pr.put("Sala",SalaDAO.getSalaObjectById(projekcija.getSalaId()).getNaziv());
						pr.put("TipProjekcije",projekcija.getTipProjekcije());
						pr.put("Cena",projekcija.getCijenaKarte());
						lista.add(pr);
					}
	    			odg.put("lista",lista);
	    		}
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		message = "Molimo Vas da proverite vase unose.";
	    		status = false;
	    	}
	    	if(lista.size()>0) {
	    		status = true;
	    		message = "Ucitano";
	    	}
	    	else {
	    		message = "Ni jedna projekcija ne ispunjava zadate kriterijume.";
	    	}
	    	odg.put("status",status);
	    	odg.put("message", message);
	    	return odg;
		}
	
		private JSONObject ucitajJednuProjekciju(HttpServletRequest request) {
			JSONObject response = new JSONObject(); 
			Sala sala = null; 
			Film film = null; 
			//obavezno ovja format 
			//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			JSONObject odg = new JSONObject(); 

			try {
				int id = Integer.valueOf(request.getParameter("idProjekcije"));
				Projekcija p = ProjekcijeDAO.getProjekcijaById(id); 
				sala = SalaDAO.getSalaObjectById(Integer.valueOf(p.getSalaId())); 
				film = FilmDAO.getFilmObjectById(Integer.valueOf(p.getFilmId())); 
				//odg.put("id", p.getId()); 
				//mogu li da stavim film.getid
				odg.put("idProjekcije", p.getId());
	    		odg.put("idFilma", p.getFilmId());
	    		odg.put("nazivFilma",film.getNaziv());
	    		odg.put("tipProjekcije",p.getTipProjekcije());
	    		odg.put("idSale",sala.getId());
	    		odg.put("nazivSale",sala.getNaziv());
	    		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");   
	    		String date = df.format(p.getDatumPrikazivanje());
	    		odg.put("terminProjekcije",date);
	    		odg.put("cijenaKarte",p.getCijenaKarte());
	    		odg.put("status",p.getStatus());
	    		int brojKarata = p.getMaxKarata() - p.getProdanoKarata();
	    		odg.put("brojKarata",brojKarata);
	    	
	    		response.put("projekcija", odg);
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Projekcija se nije ucitala "); 
			}
			
			
			return odg; 
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
}