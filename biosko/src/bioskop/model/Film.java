package bioskop.model;

import java.util.ArrayList;

public class Film  {
	private int id;  
	private String naziv;
	private String reziser; 
	private String glumci;  
	private ArrayList<Zanr> zanr;
	private int trajanje; 
	private String distributer;
	private String zemljaPorijekla;
	private int godinaProizvodnje;
	private String opis;
	
	public Film(int id, String naziv, String reziser, String glumci, ArrayList<Zanr> zanr, int trajanje,
			String distributer, String zemljaPorijekla, int godinaProizvodnje, String opis) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.reziser = reziser;
		this.glumci = glumci;
		this.zanr = zanr;
		this.trajanje = trajanje;
		this.distributer = distributer;
		this.zemljaPorijekla = zemljaPorijekla;
		this.godinaProizvodnje = godinaProizvodnje;
		this.opis = opis;
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
	public String getReziser() {
		return reziser;
	}
	public void setReziser(String reziser) {
		this.reziser = reziser;
	}
	public String getGlumci() {
		return glumci;
	}
	public void setGlumci(String glumci) {
		this.glumci = glumci;
	}
	public ArrayList<Zanr> getZanr() {
		return zanr;
	}
	public void setZanr(ArrayList<Zanr> zanr) {
		this.zanr = zanr;
	}
	public int getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}
	public String getDistributer() {
		return distributer;
	}
	public void setDistributer(String distributer) {
		this.distributer = distributer;
	}
	public String getZemljaPorijekla() {
		return zemljaPorijekla;
	}
	public void setZemljaPorijekla(String zemljaPorijekla) {
		this.zemljaPorijekla = zemljaPorijekla;
	}
	public int getGodinaProizvodnje() {
		return godinaProizvodnje;
	}
	public void setGodinaProizvodnje(int godinaProizvodnje) {
		this.godinaProizvodnje = godinaProizvodnje;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
}

