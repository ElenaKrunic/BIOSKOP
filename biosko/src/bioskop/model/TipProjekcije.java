package bioskop.model;

import java.util.ArrayList;

public class TipProjekcije {
	private int id;
	private String naziv;
	
	public TipProjekcije(int iD2, String naziv2) {
		this.id = iD2; 
		this.naziv = naziv2; 
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	
}


