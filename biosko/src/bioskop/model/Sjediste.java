package bioskop.model;

public class Sjediste {
	private int redniBroj;
	private Sala sala;
	
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
}
