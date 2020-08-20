package bioskopp;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import biosko.DAO.FilmDAO;
import biosko.DAO.KarteDAO;
import biosko.DAO.KorisnikDAO;
import biosko.DAO.ProjekcijeDAO;
import biosko.DAO.SalaDAO;
import bioskop.model.Film;
import bioskop.model.Karta;
import bioskop.model.Korisnik;
import bioskop.model.Projekcija;
import bioskop.model.Sala;
import bioskop.model.Sjediste;

/**
 * Servlet implementation class KarteServlet
 */
public class KarteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KarteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter ispis = response.getWriter(); 
		String action = request.getParameter("action"); 
		String kartaId = request.getParameter("kartaId");
		
		if(action!=null && request!=null) {
			switch(action) {
			case "izaberiSjediste" : 
				ispis.print(izaberiSjediste(request));
				break;
				
			case "kupiKartu" : 
				ispis.print(kupiKartu(request)); 
				break;
				
			case "ucitajKartuZaKorisnika":
				Korisnik korisnik = KorisnikDAO.get(request.getParameter("id"));
				  
				if(korisnik!=null) {
					String korisnickoIme = korisnik.getKorisnickoIme(); 
					ispis.print(ucitajKartu(request,korisnickoIme));
					//System.out.println("Korisnik iz baze je " + korisnik); 	
				}
				break;
			
			case "obrisiKartu": 
				ispis.print(obrisiKartu(request));
				break;
			
			}

		}
	}
private JSONObject obrisiKartu(HttpServletRequest request) {
		JSONObject response = new JSONObject(); 
		String id = request.getParameter("idKarta"); 
		boolean status = false;
		try {
			 Karta karta = KarteDAO.getKartaObjectById(id);
			 if(karta != null) {
				 status = KarteDAO.obrisiKartu(id); 
			 }
			 
		} catch(Exception e) {
			e.printStackTrace();
			status = false; 
		}
		
		response.put("status",status);
		return response; 
	}

private JSONObject ucitajJednuKartu(String kartaId) {
		JSONObject response = new JSONObject(); 
		JSONObject karta = null; 
		boolean status = false; 
		try {
			karta = KarteDAO.getById(kartaId); 
			status = true;
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		
		response.put("karta", karta); 
		response.put("status",status); 
		return response;
	}

private JSONObject ucitajKartu(HttpServletRequest request, String korisnickoIme) {
	JSONObject response = new JSONObject(); 
	boolean status = false; 
	
	try {
		ArrayList<Karta> karte = KarteDAO.ucitajKartu(korisnickoIme);
		if(karte != null) {
			ArrayList<JSONObject> jsonKarte = new ArrayList<JSONObject>(); 
			for(Karta obicnaKarta: karte) {
				Projekcija projekcija = ProjekcijeDAO.getProjekcijaById(Integer.valueOf(obicnaKarta.getIdProjekcije()));
				JSONObject jsonKarta = new JSONObject(); 
				jsonKarta.put("ID", obicnaKarta.getId()); 
				Film film = FilmDAO.getFilmObjectById(projekcija.getFilmId());
				jsonKarta.put("FilmID", film.getId()); 
				jsonKarta.put("NazivFilma", film.getNaziv()); 
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
				String termin = format.format(projekcija.getDatumPrikazivanje()); 
				jsonKarta.put("Termin", termin);
				jsonKarta.put("ProjekcijaID", projekcija.getId()); 
				jsonKarta.put("TipProjekcije", projekcija.getTipProjekcije());
				Sala sala = SalaDAO.getSalaObjectById(projekcija.getSalaId());
				jsonKarta.put("Sala", sala.getNaziv()); 
				jsonKarta.put("Sjediste", obicnaKarta.getOznakaSjedista()); 
				jsonKarta.put("Cijena",projekcija.getCijenaKarte()); 
				jsonKarte.add(jsonKarta); 
				status = true; 
			}
			response.put("jsonKarte", jsonKarte);
			
			System.out.println("Odgovor koji saljem je " + response);

		}
	} catch(Exception e) {
		e.printStackTrace(); 
	}
	response.put("status",status); 
	return response;
	}

/*
	private JSONObject ucitajKartu(HttpServletRequest request) {
		JSONObject response = new JSONObject(); 
		boolean status = false; 
		
		try {
			//ArrayList<Karta> karte = KarteDAO.ucitajKartu(korisnickoIme);
			if(karte != null) {
				ArrayList<JSONObject> jsonKarte = new ArrayList<JSONObject>(); 
				for(Karta obicnaKarta: karte) {
					Projekcija projekcija = ProjekcijeDAO.getProjekcijaById(Integer.valueOf(obicnaKarta.getIdProjekcije()));
					JSONObject jsonKarta = new JSONObject(); 
					jsonKarta.put("ID", obicnaKarta.getId()); 
					Film film = FilmDAO.getFilmObjectById(projekcija.getFilmId());
					jsonKarta.put("FilmID", film.getId()); 
					jsonKarta.put("NazivFilma", film.getNaziv()); 
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
					String termin = format.format(projekcija.getDatumPrikazivanje()); 
					jsonKarta.put("Termin", termin);
					jsonKarta.put("ProjekcijaID", projekcija.getId()); 
					jsonKarta.put("TipProjekcije", projekcija.getTipProjekcije());
					Sala sala = SalaDAO.getSalaObjectById(projekcija.getSalaId());
					jsonKarta.put("Sala", sala.getNaziv()); 
					jsonKarta.put("Sjediste", obicnaKarta.getOznakaSjedista()); 
					jsonKarta.put("Cijena",projekcija.getCijenaKarte()); 
					jsonKarte.add(jsonKarta); 
					status = true; 
				}
				response.put("jsonKarte", jsonKarte); 
			}
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		response.put("status",status); 
		return response;
	}
*/
	private JSONObject kupiKartu(HttpServletRequest request) {
		JSONObject response = new JSONObject(); 
		boolean status = false; 
		String idProjekcije = request.getParameter("id"); 
		String sjedista = request.getParameter("odabranaSjedista"); 
		String korisnickoIme = (String) request.getSession().getAttribute("userName"); 
		
		status = KarteDAO.kupiKartu(idProjekcije,sjedista,korisnickoIme); 
		
		response.put("status", status); 
		
		return response;
	}

	private JSONObject izaberiSjediste(HttpServletRequest request) {
		JSONObject response = new JSONObject(); 
		String id = request.getParameter("idProjekcije");
		Film film = new Film(); 
		boolean status = false; 
		Sala sala = new Sala(); 
		int maksimalanBrojSjedistaSale = 0; 
		Projekcija projekcija = new Projekcija();
		ArrayList<Sjediste> listaSjedista = new ArrayList<Sjediste>();
		ArrayList<String> lista = new ArrayList<String>();
		try {
			projekcija = ProjekcijeDAO.getProjekcijaById(Integer.valueOf(id)); 
			if(projekcija != null) {
				//listaSjedista = SalaDAO.slobodnaSjedistaSale(id);
				listaSjedista = SalaDAO.slobodnaSjedistaSale(String.valueOf(projekcija.getId()));
				for(Sjediste jednoSjediste : listaSjedista) {
					lista.add(String.valueOf(jednoSjediste.getRedniBroj())); 
				}
				
				maksimalanBrojSjedistaSale = SalaDAO.brojMaksimumSedistaSale(String.valueOf(projekcija.getSalaId())); 
				film = FilmDAO.getFilmObjectById(projekcija.getFilmId()); 
				System.out.println("Maksimalan broj sjedista u sali je " + maksimalanBrojSjedistaSale); 
				System.out.println("Film je " + film); 
				if (film  != null){
					JSONObject jsonFilm = new JSONObject(); 
					jsonFilm.put("idProjekcije", projekcija.getId()); 
					jsonFilm.put("slobodnaSedista", lista);
					//DateFormat df = new SimpleDateFormat(); 
					//jsonFilm.put("termin", projekcija.getDatumPrikazivanje()); 
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
					jsonFilm.put("termin", format.format(projekcija.getDatumPrikazivanje())); 
					jsonFilm.put("cenaKarte", projekcija.getCijenaKarte()); 
					jsonFilm.put("nazivFilma", film.getNaziv()); 
					jsonFilm.put("tipProjekcije", projekcija.getTipProjekcije()); 
					jsonFilm.put("idSale", projekcija.getSalaId());
					//jsonFilm.put("nazivSale", SalaDAO.getSalaObjectById(projekcija.getId()).getNaziv());
					jsonFilm.put("nazivSale", SalaDAO.getSalaObjectById(projekcija.getSalaId()).getNaziv());
					jsonFilm.put("trajanje",film.getTrajanje()); 
					jsonFilm.put("maxSjedista", maksimalanBrojSjedistaSale);
					
					
					response.put("jsonFilm",jsonFilm);
					status=true; 	
				} else {
					System.out.println("Nema filma"); 
				}
				
				
			} else {
				System.out.println("Nema projekcije"); 
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		response.put("status",status); 
		System.out.println("Status je " + status); 
		System.out.println("Response je " + response); 
		return response;
	}

}
