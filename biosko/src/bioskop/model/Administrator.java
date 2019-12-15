package bioskop.model;

import java.util.ArrayList;

public class Administrator {
	private ArrayList<Projekcija> projekcije;

	public ArrayList<Projekcija> getProjekcije() {
		return projekcije;
	}

	public void setProjekcije(ArrayList<Projekcija> projekcije) {
		this.projekcije = projekcije;
	}

	public Administrator(ArrayList<Projekcija> projekcije) {
		super();
		this.projekcije = projekcije;
	}
}
