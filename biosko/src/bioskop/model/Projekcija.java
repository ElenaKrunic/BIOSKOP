package bioskop.model;

import java.util.Date;

public class Projekcija {
	private int id; 
	private int filmId;
	private String tipProjekcije; 
	private int salaId; 
	private Date datumPrikazivanje;
	private double cijenaKarte;
	private String adminDodaoProjekciju;
	private String status; 
	private int maxKarata; 
	private int prodanoKarata;
	
	public Projekcija(int id, int filmId, String tipProjekcije, int salaId,
			Date datumPrikazivanje, double cijenaKarte, String adminDodaoProjekciju,String status,int maxKarata,int prodanoKarata) {
		super();
		this.id = id;
		this.filmId = filmId; 
		this.tipProjekcije = tipProjekcije; 
		this.salaId = salaId; 
		this.datumPrikazivanje = datumPrikazivanje; 
		this.cijenaKarte = cijenaKarte; 
		this.adminDodaoProjekciju = adminDodaoProjekciju; 
		this.status = status; 
		this.maxKarata = maxKarata; 
		this.prodanoKarata = prodanoKarata; 
	}
	public Projekcija(int id2, Integer valueOf, String naziv, Integer valueOf2, String pocetakProjekcije,
			Double valueOf3, String administrator, String status2, int brojMaksimumSedistaSale, int prodanoKarata2) {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public String getTipProjekcije() {
		return tipProjekcije;
	}
	public void setTipProjekcije(String tipProjekcije) {
		this.tipProjekcije = tipProjekcije;
	}
	public int getSalaId() {
		return salaId;
	}
	public void setSalaId(int salaId) {
		this.salaId = salaId;
	}
	public Date getDatumPrikazivanje() {
		return datumPrikazivanje;
	}
	public void setDatumPrikazivanje(Date datumPrikazivanje) {
		this.datumPrikazivanje = datumPrikazivanje;
	}
	public double getCijenaKarte() {
		return cijenaKarte;
	}
	public void setCijenaKarte(double cijenaKarte) {
		this.cijenaKarte = cijenaKarte;
	}
	public String getAdminDodaoProjekciju() {
		return adminDodaoProjekciju;
	}
	public void setAdminDodaoProjekciju(String adminDodaoProjekciju) {
		this.adminDodaoProjekciju = adminDodaoProjekciju;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getMaxKarata() {
		return maxKarata;
	}
	public void setMaxKarata(int maxKarata) {
		this.maxKarata = maxKarata;
	}
	public int getProdanoKarata() {
		return prodanoKarata;
	}
	public void setProdanoKarata(int prodanoKarata) {
		this.prodanoKarata = prodanoKarata;
	} 
	
	
	
}
