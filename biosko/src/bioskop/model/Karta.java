package bioskop.model;

import java.util.Date;

public class Karta {
	private int id;
	private Film film;
	private TipProjekcije tipProjekcije;
	private Sala sala; 
	private Date datumProdajeKarte;
	private Korisnik kupljenaKarta;
	
	public Karta(int id, Film film, TipProjekcije tipProjekcije, Sala sala, Date prodajeKarte, Korisnik kupioKartu) {
		this.id = id;
		this.film = film;
		this.tipProjekcije = tipProjekcije;
		this.sala = sala;
		this.datumProdajeKarte = prodajeKarte;
		this.kupljenaKarta= kupioKartu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public TipProjekcije getTipProjekcije() {
		return tipProjekcije;
	}

	public void setTipProjekcije(TipProjekcije tipProjekcije) {
		this.tipProjekcije = tipProjekcije;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Date getProdajeKarte() {
		return datumProdajeKarte;
	}

	public void setProdajeKarte(Date prodajeKarte) {
		this.datumProdajeKarte = prodajeKarte;
	}

	public Korisnik getKupioKartu() {
		return kupljenaKarta;
	}

	public void setKupioKartu(Korisnik kupioKartu) {
		this.kupljenaKarta = kupioKartu;
	}
	
	
}
