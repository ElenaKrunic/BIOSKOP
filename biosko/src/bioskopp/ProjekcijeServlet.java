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
				try {
					ispis.print(filtriranjeProjekcije(request));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		String administrator = (String)request.getSession().getAttribute("userName");
		Calendar kalendar = Calendar.getInstance();

		String filmID = request.getParameter("projekcijaId"); 
		String pocetakProjekcije = request.getParameter("pocetakProjekcije"); 
		String idSale = request.getParameter("salaProjekcije"); 
		String idTipProjekcije = request.getParameter("tipProjekcije"); 
		String cijenaProjekcije = request.getParameter("cijenaProjekcije"); 
		
		//System.out.println("Ovo je idSale parametar u servletu " +  idSale); 
		try {
			
			if(filmID==null || filmID=="") {
				filmID  = "%%";
        	}
        	if(idSale==null || idSale=="") {
        		idSale  = "%%";
        	}
        	if(idTipProjekcije==null || idTipProjekcije=="") {
        		idTipProjekcije  = "%%";
        	}
        	if(pocetakProjekcije.length()!=16) {
        		pocetakProjekcije = "2000-01-01 00:00";
        	}
     
			Film film = FilmDAO.getFilmObjectById(Integer.valueOf(filmID)); 
					
			DateFormat formatPocetakProjekcije = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    		Date tacanDatum = formatPocetakProjekcije.parse(pocetakProjekcije);
    		kalendar.setTime(tacanDatum);
    		//int field int amount
			kalendar.add(Calendar.MINUTE, film.getTrajanje());
			tacanDatum = kalendar.getTime();
			DateFormat formatKrajProjekcije = new SimpleDateFormat("yyyy-MM-dd HH:mm");       
			String krajTermina = formatKrajProjekcije.format(tacanDatum);
			
			TipProjekcije tipProjekcije = TipProjekcijeDAO.getTipProjekcijeObjectById(Integer.valueOf(idTipProjekcije));
			
    		Date formatiranPocetakProjekcije = formatPocetakProjekcije.parse(pocetakProjekcije);	
			Projekcija projekcija = new Projekcija(1, Integer.valueOf(filmID), tipProjekcije.getNaziv(), Integer.valueOf(idSale), formatiranPocetakProjekcije, Double.valueOf(cijenaProjekcije), administrator, "Active", SalaDAO.maksimalnoSjedistaSale(idSale), 0);
			
			if(projekcija !=null) {
				status = ProjekcijeDAO.dodajProjekciju(projekcija, krajTermina); 
				//System.out.println("Status u servletu " + status);
			}
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
				   response.put("sale", sale);
				   status = true;
			   }catch(Exception e) {
				   e.printStackTrace();
			   }
			   
			   response.put("status", status);
			   return response;
	}

	private JSONObject ucitajFilm(HttpServletRequest request)  {
			 JSONObject response = new JSONObject();
			   boolean status = false;
			   
			   try {
				   ArrayList<JSONObject> filmovi = FilmDAO.getMovies("", 0, "", "", "", "", "", "", "");
				   response.put("filmovi", filmovi);
				   status = true;
			   }catch(Exception e) {
				   e.printStackTrace();
			   }
			   response.put("status", status);
			   return response;
	}

	private JSONObject ucitajSale() {
		JSONObject response = new JSONObject(); 
		boolean status = false; 
		try {
			ArrayList<String> listaSala = SalaDAO.getSale();
			response.put("listaSala",listaSala);
			status =true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		//ArrayList<String> listaSala = SalaDAO.getSale();
		//if(listaSala.size() > 0) {
			//status = true; 
		//}
		//response.put("listaSala", listaSala);
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
		
	private JSONObject filtriranjeProjekcije(HttpServletRequest request) throws Exception {
			JSONObject response = new JSONObject(); 
			boolean status = false; 
			
			ArrayList<JSONObject> lista = new ArrayList<JSONObject>(); 
			
			String idFilma = request.getParameter("filmId");
	    	String pocetak = request.getParameter("pocetakProjekcije");
	    	String kraj = request.getParameter("krajProjekcije");
	    	String idSale = request.getParameter("idSala");
	    	String tipProjekcije = request.getParameter("tipProjekcije");
	    	String cijenaMin = request.getParameter("cijenaMin");
	    	String cijenaMax = request.getParameter("cijenaMax");
	    	
	    	try {
	    	
				if(idFilma==null || idFilma=="") {
	        		idFilma  = "%%";
	        	}
	        	if(idSale==null || idSale=="") {
	        		idSale  = "%%";
	        	}
	        	if(tipProjekcije==null || tipProjekcije=="") {
	        		tipProjekcije  = "%%";
	        	}
	        	if(!(Integer.valueOf(cijenaMin)>0)) {
	        		cijenaMin = String.valueOf(0);
	        	}
	        	if(!(Integer.valueOf(cijenaMax)>0)) {
	        		cijenaMax = String.valueOf(0);
	        	}
	        	
	        	if(pocetak.length()!=16) {
	        		pocetak = "2000-01-01 00:00";
	        	}
	        	if(kraj.length()!=16) {
	        		kraj = "2022-01-01 00:00";
	        	}
	   		JSONObject filtriranaProjekcija = ProjekcijeDAO.filterProjekcije(idFilma, pocetak, kraj, idSale, tipProjekcije, cijenaMin, cijenaMax);
	   		if((Boolean) filtriranaProjekcija.get("status")) {
	   			ArrayList<Projekcija> listaObicnih = (ArrayList<Projekcija>) filtriranaProjekcija.get("listaProjekcija");
		    	
		    	for(Projekcija obicnaProjekcija : listaObicnih) {
		    		JSONObject jsonProjekcija = new JSONObject(); 
		    		jsonProjekcija.put("ID",obicnaProjekcija.getId()); 
		    		jsonProjekcija.put("nazivFilma", FilmDAO.getFilmObjectById(obicnaProjekcija.getFilmId()).getNaziv());
		    		jsonProjekcija.put("idFilma", obicnaProjekcija.getFilmId());
		    		DateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		    		String termin = d.format(obicnaProjekcija.getDatumPrikazivanje()); 
		    		jsonProjekcija.put("termin", termin); 
		    		jsonProjekcija.put("sala", SalaDAO.getSalaObjectById(obicnaProjekcija.getSalaId()).getNaziv());
		    		jsonProjekcija.put("tip", obicnaProjekcija.getTipProjekcije());
		    		jsonProjekcija.put("cijena", obicnaProjekcija.getCijenaKarte());
		    		lista.add(jsonProjekcija);
		    		status = true;
		    	}
		    	
		    	response.put("lista", lista);
	   		}
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    		status = false; 
	    	}

	    	response.put("status",status);
	    	
	    	System.out.println("Filtrirana projekcija koju saljem na js je " + lista);
			return response; 
		}
	    	//status false 
    		
	   		//System.out.println("Projekcija koju filtriram je " + filtriranaProjekcija); 
	    		
	    	//System.out.println("Id filma koji filriram je " + idFilma); 
		    		//System.out.println("Status filtrirane projekcije je " + status); 
	   		
	   		//lista projekcija key value iz dao filtriraj pokupim
	   		
		
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
		
	@SuppressWarnings("unused")
	private JSONObject ucitajProjekcijeZaDanasMetoda(HttpServletRequest request) {
		JSONObject response = new JSONObject(); 
		ArrayList<JSONObject> listaProjekcija = new ArrayList<JSONObject>();
		
		boolean status = false; 
		
		//srediti i poslati u metodu ucitajzadatum
		//srediti datum za poslati u metodu 
		Date datumZaMetodu = new Date(); 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		
		String datum = format.format(datumZaMetodu);
		
		ArrayList<Projekcija> lista = ProjekcijeDAO.ucitajProjekcijuZaDanasnjiDatum(request, datum);
		
		for(Projekcija obicnaProjekcija: lista) {
			Film film = null;
			Sala sala = null; 
			try {
				film = FilmDAO.getFilmObjectById(obicnaProjekcija.getFilmId());
				sala = SalaDAO.getSalaObjectById(obicnaProjekcija.getSalaId()); 
				
				JSONObject jsonProjekcija = new JSONObject(); 
				
				if(obicnaProjekcija != null || film!= null || sala!=null) {
					jsonProjekcija.put("idProjekcije", obicnaProjekcija.getId());
					jsonProjekcija.put("idFilma", obicnaProjekcija.getFilmId());
					jsonProjekcija.put("nazivFilma",film.getNaziv());
					
					DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
					String terminProjekcije = format2.format(obicnaProjekcija.getDatumPrikazivanje());
					jsonProjekcija.put("terminProjekcije", terminProjekcije);
					
					jsonProjekcija.put("nazivSale",sala.getNaziv()); 
					jsonProjekcija.put("tipProjekcije", obicnaProjekcija.getTipProjekcije()); 
					jsonProjekcija.put("cijena", obicnaProjekcija.getCijenaKarte());
				} 
				else {
					throw new Exception();
				}
				listaProjekcija.add(jsonProjekcija);
				status = true; 
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response.put("listaProjekcija",listaProjekcija);
		response.put("status",status); 
		return response; 
	}
		
		
}