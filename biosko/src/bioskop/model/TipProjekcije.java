package bioskop.model;

import java.util.ArrayList;

public class TipProjekcije {
	private int id;
	public ArrayList<String> tipoviProjekcije;
	private String naziv;
	
	public TipProjekcije(int id, ArrayList<String> tipoviProjekcije,String naziv) {
		this.id = id;
		this.tipoviProjekcije = tipoviProjekcije;
		this.setNaziv(naziv);
		tipoviProjekcije.add("2D");
		tipoviProjekcije.add("3D");
		tipoviProjekcije.add("4D");
		
		//tipoviProjekcije.contains("2D");
		//tipoviProjekcije.contains("3D");
		//tipoviProjekcije.contains("4D");

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	
	public ArrayList<String> getTipoviProjekcije() {
		return tipoviProjekcije;
	}
	public void setTipoviProjekcije(ArrayList<String> tipoviProjekcije) {
		this.tipoviProjekcije = tipoviProjekcije;

	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		if(tipoviProjekcije.contains(naziv)) {
		this.naziv = naziv;
		}
		else {
			this.naziv = "4D";
		}
	}	
}



