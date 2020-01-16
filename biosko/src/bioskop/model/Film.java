package bioskop.model;

public class Film  {
	
	
	private String id;  
	private String naziv;
	private String reziser; 
	private String glumci;  
	private Zanr zanr;
	private String trajanje; 
	private String distributer;
	private String zemljaPorijekla;
	private int godinaProizvodnje;
	private String opis;
	
	public Film (String id, String naziv, String reziser, String glumci, Zanr zanr, String trajanje, String distributer,
			String zemljaPorijekla, int godinaProizvodnje, String opis) {
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
	
	public Film (String id,String naziv, Zanr zanr, String trajanjeFilma, String distributer,
			String zemljaPorijekla, int godinaProizvodnje) {
		this.id = id;
		this.naziv = naziv;
		this.zanr = zanr;
		this.trajanje = trajanjeFilma;
		this.distributer = distributer;
		this.zemljaPorijekla = zemljaPorijekla;
		this.godinaProizvodnje = godinaProizvodnje;
	}
	
	public Film (String naziv, Zanr zanr, String trajanje, String distributer,
			String zemljaPorijekla, int godinaProizvodnje) {
		this.naziv = naziv;
		this.zanr = zanr;
		this.trajanje = trajanje;
		this.distributer = distributer;
		this.zemljaPorijekla = zemljaPorijekla;
		this.godinaProizvodnje = godinaProizvodnje;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Zanr getZanr() {
		return zanr;
	}

	public void setZanr(Zanr zanr) {
		this.zanr = zanr;
	}

	public String getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(String trajanje) {
		if (trajanje != null) {
		this.trajanje = trajanje; 
		}
		else {
			this.trajanje = null;
		}
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
		if (godinaProizvodnje > 0) {
		this.godinaProizvodnje = godinaProizvodnje;
		}
		else {
			this.godinaProizvodnje = 1;
		} 
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}	
}

