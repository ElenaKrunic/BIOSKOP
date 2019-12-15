package bioskop.model;

public class Projekcija {
	private int id; 
	private Film film;
	private TipProjekcije tipProjekcije; 
	private Sala sala; 
	private int datumPrikazivanje;
	private int cijenaKarte;
	private Administrator dodaoProjekciju;
	
	public Projekcija(int id, Film film, TipProjekcije tipProjekcije, Sala sala, int datumPrikazivanje, int cijenaKarte,
			Administrator dodaoProjekciju) {
		this.id = id;
		this.film = film;
		this.tipProjekcije = tipProjekcije;
		this.sala = sala;
		this.datumPrikazivanje = datumPrikazivanje;
		this.cijenaKarte = cijenaKarte;
		this.dodaoProjekciju = dodaoProjekciju;
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

	public int getDatumPrikazivanje() {
		return datumPrikazivanje;
	}

	public void setDatumPrikazivanje(int datumPrikazivanje) {
		this.datumPrikazivanje = datumPrikazivanje;
	}

	public int getCijenaKarte() {
		return cijenaKarte;
	}

	public void setCijenaKarte(int cijenaKarte) {
		this.cijenaKarte = cijenaKarte;
	}

	public Administrator getDodaoProjekciju() {
		return dodaoProjekciju;
	}

	public void setDodaoProjekciju(Administrator dodaoProjekciju) {
		this.dodaoProjekciju = dodaoProjekciju;
	}
}
