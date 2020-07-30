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
		PrintWriter out = response.getWriter(); 
		String action = request.getParameter("action"); 
		
		if(action != null && request != null) {
			switch(action) {
			case "danasProjekcije": 
				out.print(projekcijeZaDanas(request));
				break;
			}
		}
	}

	private JSONObject projekcijeZaDanas(HttpServletRequest request) {
		JSONObject response = new JSONObject();
		JSONObject jsonProjekcija = new JSONObject(); 
		ArrayList<JSONObject> jsonListaProjekcija = new ArrayList<JSONObject>(); 
		ArrayList<Projekcija> obicneDanasProjekcije = null; 
		Date date = new Date();
		//obavezno ovaj format
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String danasnjiDatum = df.format(date); 
		//ucitaj listu projekcija za danasjji datum 
		obicneDanasProjekcije = ProjekcijeDAO.ucitajZaDanasnjiDatum(request,danasnjiDatum); 
		
		for(Projekcija danasnjaProjekcija : obicneDanasProjekcije ) {
			try {
				//pronadji film i salu po idu 
				Film film = null; 
				Sala sala = null;
				film = FilmDAO.getFilmObjectById(danasnjaProjekcija.getFilmId());
				sala = SalaDAO.getSalaObjectById(danasnjaProjekcija.getSalaId());
				
				if(film != null && sala != null) {
					jsonProjekcija.put("idProjekcije", danasnjaProjekcija.getId()); 
					jsonProjekcija.put("idFilma", danasnjaProjekcija.getFilmId()); 
					jsonProjekcija.put("nazivFilma", film.getNaziv());
					DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
					String datumProjekcije = dateF.format(danasnjaProjekcija.getDatumPrikazivanje()); 
					jsonProjekcija.put("terminProjekcije", datumProjekcije);
					jsonProjekcija.put("tipProjekcije", danasnjaProjekcija.getTipProjekcije()); 
					jsonProjekcija.put("idSale", sala.getId()); 
					jsonProjekcija.put("nazivSale", sala.getNaziv()); 
					jsonProjekcija.put("cijenaKarte", danasnjaProjekcija.getCijenaKarte()); 	
				}
				else {
					System.out.println("Film ili sala su null "); 
				}
				jsonListaProjekcija.add(jsonProjekcija); 
			} catch(Exception e ) {
				System.out.println("Greska "); 
			}
		}
		
		response.put("jsonListaProjekcija", jsonListaProjekcija);
		return response; 
		
		}
}
