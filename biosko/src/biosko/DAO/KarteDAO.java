package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;

import bioskop.model.Karta;
import bioskop.model.Projekcija;

public class KarteDAO {
	
	public static Karta getKartaObjectById(String id) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		Karta karta = null; 
		
		try {
			String query = "SELECT ID,ID_Projekcije,ID_Sedista,VremeProdaje,Korisnik FROM Karta WHERE ID=?";

			prep = conn.prepareStatement(query);
			prep.setString(1,id);

			rs = prep.executeQuery();
			
			if (rs.next()) {
				int index = 1;
				String ajdi = rs.getString(index++);
				int ID_Karte = Integer.valueOf(ajdi);
				String ID_Projekcije = rs.getString(index++);
				String ID_Sjedista = rs.getString(index++);
				String oznakaSjedista = String.valueOf(SalaDAO.getSjedisteObjectById(ID_Sjedista).getRedniBroj());
				String VrijemeProdaje = rs.getString(index++);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date datum = df.parse(VrijemeProdaje);
				String korisnik = rs.getString(index++);
				karta = new Karta(ID_Karte, ID_Projekcije, oznakaSjedista, datum, korisnik);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return karta; 
	}
	
	
	public static ArrayList<Karta> listaKarataZaProjekciju(String idProjekcije) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		Karta karta = new Karta(); 
		
		ArrayList<Karta> listaKarata = new ArrayList<>(); 
		
		try {
			String query = "SELECT ID,ID_Projekcije,ID_Sedista,VremeProdaje,Korisnik FROM Karta WHERE ID_Projekcije=?";

			prep = conn.prepareStatement(query); 
			prep.setString(1,idProjekcije); 
			
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String idKarte = rs.getString(index++);
				int id = Integer.valueOf(idKarte);
				String idP = rs.getString(index++); 
				String idSjedista = rs.getString(index++); 
				String oznakaSjedista = String.valueOf(SalaDAO.getSjedisteObjectById(idSjedista).getRedniBroj()); 
				String vrijemeProdaje = rs.getString(index++); 
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date datum = format.parse(vrijemeProdaje); 
				String korisnik = rs.getString(index++); 
				 
				karta = new Karta(id, idP,oznakaSjedista, datum,korisnik);
				listaKarata.add(karta); 
			}
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return listaKarata;
	}
	
	/*
	public static boolean kupiKartu2(String idProjekcije, String sjedista, String username) {
		boolean status = true; 
		
		try {
			Projekcija projekcija = ProjekcijeDAO.getProjekcijaById(Integer.valueOf(idProjekcije));
			
			System.out.println("Dobavljanje projekcije iz kupi kartu " + projekcija); 
				String[] viseSjedista = sjedista.split(";"); 
				ArrayList<String> nizSjedista = new ArrayList<String>(); 
				
				for(int i =0; i<viseSjedista.length; i++) {
					String pojedinacnoSjediste = viseSjedista[i];
						int brojSjedista = Integer.valueOf(pojedinacnoSjediste);						
						int dodajSjediste = brojSjedista+1;
						
						if((i+1) <viseSjedista.length) {
							if(!(dodajSjediste==Integer.valueOf(viseSjedista[i+1]))) {
								System.out.println("Sjedista nisu jedno do drugog");
								throw new Exception();
							} else {
								 
							}
						}
						nizSjedista.add(pojedinacnoSjediste);
						System.out.println("Sjediste dodato u niz sjedista,ima vrijednost: " + pojedinacnoSjediste); 
				}
					
				for(String jednoSjediste : nizSjedista) {
					String idSjedista = SalaDAO.getSjedisteId(String.valueOf(projekcija.getSalaId()), jednoSjediste);
					if(!uzmiKartu(idProjekcije,idSjedista,username)) {
						System.out.println("Preuzimanje karte za sjediste");
						status = false;
					}
				}
				
				
			
		} catch(Exception e ) {
			e.printStackTrace();
			
		}
		
		return status; 
	}
	*/
	
	public static boolean kupiKartu(String idProjekcije,String sedista,String username) {
		boolean status = true;
		try {
			Projekcija projekcija = ProjekcijeDAO.getProjekcijaById(Integer.valueOf(idProjekcije));
			//svako sjediste splitovano mi po ;
			String[] s = sedista.split(";");
			//
			ArrayList<String> nizS = new ArrayList<String>();
			for (int i=0;i<s.length;i++) {
				String sediste = s[i];


				//sjedista jedno do drugog 
				int sjediste = Integer.valueOf(sediste);
				int sjediste1 = sjediste+1;
				int sjediste2 = sjediste-1;
				if((i+1)<s.length) {
					if(!(sjediste2==Integer.valueOf(s[i+1]) || sjediste1==Integer.valueOf(s[i+1])))	{
						System.out.println("Sjedista nisu jedno do drugog");
						throw new Exception();
					}
				}
				nizS.add(sediste);
			}
			for (String sed : nizS) {
				String idSedista = SalaDAO.getSjedisteId(String.valueOf(projekcija.getSalaId()), sed);
				if(!uzmiKartu(idProjekcije, idSedista, username)) {
					status = false;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	/*
	public static boolean uzmiKartu(String idProjekcije, String idSjedista, String korisnik) {
		
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		boolean status = false; 
		int dodajKartu=0;
		try {
			
			String query = "INSERT INTO Karta(ID_Projekcije,ID_Sedista,VremeProdaje,Korisnik) VALUES(?,?,?,?)";
			prep = conn.prepareStatement(query); 
			
			prep.setString(1, idProjekcije);
			prep.setString(2, idSjedista);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			String date = df.format(new Date()); 
			prep.setString(3, date);
			prep.setString(4, korisnik);
			
			System.out.println("Podaci dodati u kartu su " + idProjekcije + idSjedista + korisnik);
			
			//provjera da li je slobodno sjediste 
			int provjereno =0; 
			if(SalaDAO.provjeraZaSlobodnoMjesto(idProjekcije, idSjedista)) {
				 provjereno = prep.executeUpdate();
				 System.out.println("Mjesto je slobodno, kupovina karte odradjena"); 
				 //System.out.println("Uspjesno zavrsena kupovina karte");
			} 
			
			if(provjereno==1)
				{
				status=true; 
				SalaDAO.zauzetoMjesto(idProjekcije);
				System.out.println("Mjesto je zauzeto");
				}
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return status; 
	} */
	
	public static boolean uzmiKartu(String idProjekcije, String idSjedista, String korisnik) {

		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		boolean status = false; 
		try {

			String query = "INSERT INTO Karta(ID_Projekcije,ID_Sedista,VremeProdaje,Korisnik) VALUES(?,?,?,?)";
			prep = conn.prepareStatement(query); 

			prep.setString(1, idProjekcije);
			prep.setString(2, idSjedista);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			String date = df.format(new Date()); 
			prep.setString(3, date);
			prep.setString(4, korisnik);

			prep.executeUpdate();
			status = true;

		} catch(Exception e) {
			e.printStackTrace(); 
		}

		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}

		return status; 
	}

	public static ArrayList<Karta> ucitajKartuZaKorisnickoIme(String korisnickoIme) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		ArrayList<Karta> listaKarata = new ArrayList<Karta>();
		
		try {
			
			String query = "SELECT ID,ID_Projekcije,ID_Sedista,VremeProdaje,Korisnik FROM Karta WHERE Korisnik=?";

			prep = conn.prepareStatement(query); 
			prep.setString(1, korisnickoIme);
			
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String idKarte = rs.getString(index++); 
				int ID = Integer.valueOf(idKarte); 
				
				String ID_Projekcije = rs.getString(index++); 
				//String salaId = rs.getString(index++); 
				String ID_Sjedista = rs.getString(index++); 
				String oznakaSjedista = String.valueOf(SalaDAO.getSjedisteObjectById(ID_Sjedista).getRedniBroj());

				String VrijemeProdaje = rs.getString(index++);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date datum = df.parse(VrijemeProdaje);
				
				String Korisnik = rs.getString(index++);
				
				Karta karta = new Karta(ID, ID_Projekcije, oznakaSjedista, datum, Korisnik);
				
				listaKarata.add(karta);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return listaKarata;
	}

	public static JSONObject getById(String kartaId) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		Karta karta = null; 
		
		try {
			String query = "SELECT ID,ID_Projekcije,ID_Sedista,VremeProdaje,Korisnik FROM Karta WHERE ID=?";

			prep = conn.prepareStatement(query);
			prep.setString(1,kartaId);

			rs = prep.executeQuery();
			
			if (rs.next()) {
				int index = 1;
				String ajdi = rs.getString(index++);
				int ID_Karte = Integer.valueOf(ajdi);
				String ID_Projekcije = rs.getString(index++);
				String ID_Sjedista = rs.getString(index++);
				String oznakaSjedista = String.valueOf(SalaDAO.getSjedisteObjectById(ID_Sjedista).getRedniBroj());
				String VrijemeProdaje = rs.getString(index++);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date datum = df.parse(VrijemeProdaje);
				String korisnik = rs.getString(index++);
				karta = new Karta(ID_Karte, ID_Projekcije, oznakaSjedista, datum, korisnik);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return null;
	}


	public static boolean obrisiKartu(String id) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null ;
		
		try {
			String query = "DELETE FROM Karta WHERE ID=?"; 
			
			prep = conn.prepareStatement(query); 
			prep.setString(1, id);
			
			prep.executeUpdate(); 
			System.out.println("Izvrsena exevute update u delete karta"); 
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		return false;
	}




	
}
