package bioskop.model;

import java.util.Date;
import java.util.GregorianCalendar;

public class Korisnik {
	
	private String ID; 
	private String korisnickoIme;
	private String lozinka;
	private Uloga uloga;
	private Date datumReg; 
	private String status; 
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDatumReg() {
		return datumReg;
	}

	public void setDatumReg(Date datumReg) {
		this.datumReg = datumReg;
	}
	
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
	
	public Uloga getUloga() {
		return uloga;
	}
	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	
	public Korisnik(String korisnickoIme, String lozinka,  Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.uloga = uloga;
	} 
	
	public Korisnik(String ID,String korisnickoIme, String lozinka, Uloga uloga, Date datumRegistracije, String status) {
		super();
		this.ID = ID;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.uloga = uloga;
		this.datumReg = datumRegistracije;
		this.status = status;
	}
}
