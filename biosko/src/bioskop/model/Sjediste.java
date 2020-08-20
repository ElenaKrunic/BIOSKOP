package bioskop.model;

public class Sjediste {
	private int redniBroj;
	private Sala sala;
	private int idSale;
	
	public int getRedniBroj() {
		return redniBroj;
	}
	
	public void setRedniBroj(int redniBroj) {
		this.redniBroj = redniBroj;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	} 
	
	public Sjediste(int redniBroj, Sala sala) {
		this.redniBroj = redniBroj;
		this.sala = sala;
	}

	public Sjediste() {
		// TODO Auto-generated constructor stub
	}

	public Sjediste(int idSale, int redniBroj) {
		this.idSale = idSale; 
		this.redniBroj = redniBroj;
	}

	public int getIdSale() {
		return idSale;
	}

	public void setIdSale(int idSale) {
		this.idSale = idSale;
	}
}
