package bioskopp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
				
			case "ucitajSale" :
				ispis.print(ucitajSale());
				break; 
				
			case "ucitajFilmProjekcija":
				ispis.print(ucitajFilm(request));
				break;
			}
		}
	}
	
		private JSONObject ucitajFilm(HttpServletRequest request)  {
			 JSONObject odg = new JSONObject();
			   boolean status = false;
			   String message = "Unexpected error";
			   
			   try {
				   ArrayList<JSONObject> filmovi = FilmDAO.getMovies("", 0, "", "", "", "", "", "", "");
				   odg.put("filmovi", filmovi);
				   status = true;
				   message = "Ucitano";
			   }catch(Exception e) {
				   e.printStackTrace();
			   }
			   
			   odg.put("status", status);
			   odg.put("message", message);
			   return odg;
	}

		private JSONObject ucitajSale() {
		JSONObject response = new JSONObject(); 
		ArrayList<String> listaSala = SalaDAO.getSale(); 
		boolean status = false; 
		if(listaSala.size() > 0) {
			status = true; 
		}
		response.put("status",status); 
		response.put("listaSala",listaSala); 
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
			
			String naziv = request.getParameter("nazivFilma");
			//kako da rijesim kraj termina 
			String termin = request.getParameter("termin");
			//za ovo mi tebaju id 
			String sala = request.getParameter("sala"); 
			String tip = request.getParameter("tip"); 
			int cijena = 322;
			try {
				 cijena = Integer.valueOf(request.getParameter("cijena"));
			} catch(Exception e) {
				System.out.println("Greska kod cijene projekcije"); 
			}
			return null; 
			/*
			JSONObject response = new JSONObject(); 
			
	    	ArrayList<JSONObject> lista = new ArrayList<JSONObject>();
			String idFilm = request.getParameter("idFilm"); 
			String pocetakProjekcije = request.getParameter("pocetakProjekcije"); 
			String zavrsetakProjekcije = request.getParameter("zavrsetakProjekcije"); 
			String salaProjekcije = request.getParameter("salaProjekcije"); 
			String tipP = request.getParameter("tipP");
			String najmanjaCijena = request.getParameter("najmanjaCijena"); 
			String najvecaCijena = request.getParameter("najvecaCijena");
			
			
			try {
		    	if(!(Integer.valueOf(najvecaCijena)>0)) {
	        		najvecaCijena = String.valueOf(0);
	        	}
	        	if(Integer.valueOf(najmanjaCijena)>Integer.valueOf(najvecaCijena)) {
	        		najvecaCijena = najmanjaCijena;
	        	}
	        	if(pocetakProjekcije.length()!=16) {
	        		pocetakProjekcije = "2000-12-31 10:10";
	        	}
	        	if(zavrsetakProjekcije.length()!=16) {
	        		zavrsetakProjekcije = "2100-12-31 14:02";
	        	}
				
	        	//System.out.println("ID FILMA : "+idFilm);
	    		JSONObject srv = ProjekcijeDAO.getProjekcije(idFilm,pocetakProjekcije,zavrsetakProjekcije,salaProjekcije,tipP,najmanjaCijena,najvecaCijena);
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
	    			response.put("lista",lista);
	    		}
			} catch(Exception e) {
				e.printStackTrace();
			}

    			
    			return response;
    			*/
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