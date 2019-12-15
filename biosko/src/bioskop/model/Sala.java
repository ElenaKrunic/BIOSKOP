package bioskop.model;

import java.util.ArrayList;

public class Sala {
	private int id;
	private String naziv;
	private ArrayList<TipProjekcije> tipProjekcije;
	
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
	public ArrayList<TipProjekcije> getTipProjekcije() {
		return tipProjekcije;
	}
	public void setTipProjekcije(ArrayList<TipProjekcije> tipProjekcije) {
		this.tipProjekcije = tipProjekcije;
	}
	public Sala(int id, String naziv, ArrayList<TipProjekcije> tipProjekcije) {
		this.id = id;
		this.naziv = naziv;
		this.tipProjekcije = tipProjekcije;
	}
	
	
	
}
