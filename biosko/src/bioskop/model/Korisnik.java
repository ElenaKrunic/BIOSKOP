package bioskop.model;

public class Korisnik {
	private String korisnickoIme;
	private String lozinka;
	private String datumRegistracije;
	private Uloga uloga;
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getDatumRegistracije() {
		return datumRegistracije;
	}
	public void setDatumRegistracije(String datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}
	public Uloga getUloga() {
		return uloga;
	}
	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	
	public Korisnik(String korisnickoIme, String lozinka, String datumRegistracije, Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.datumRegistracije = datumRegistracije;
		this.uloga = uloga;
	} 
	
	public Korisnik(String korisnickoIme, String lozinka,  Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.uloga = uloga;
	} 
}
