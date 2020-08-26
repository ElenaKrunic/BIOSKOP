package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.ws.Response;

import org.json.simple.JSONObject;

import bioskop.model.Karta;
import bioskop.model.Projekcija;
import bioskop.model.Sala;
import bioskop.model.Sjediste;
import bioskop.model.TipProjekcije;
public class SalaDAO {

	public static Sala getSalaObjectById(int salaId) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		Sala sala = null; 
		
		try {
			String query = "SELECT ID,Naziv,ID_Tipova_Projekcija FROM Sale WHERE ID = ?";
			
			prep = conn.prepareStatement(query); 
			prep.setString(1, String.valueOf(salaId));
			rs = prep.executeQuery(); 
			
			if(rs.next()) {
				int index = 1; 
				int id = Integer.valueOf(rs.getString(index++)); 
				String naziv = rs.getString(index++); 
				String[] idProjekcija = rs.getString(index++).split(";");
				ArrayList<TipProjekcije> listaTipovaProj = new ArrayList<TipProjekcije>();
				for(String idProjekcije : idProjekcija) {
					TipProjekcije tip = TipProjekcijeDAO.getTipProjekcijeObjectById(Integer.valueOf(idProjekcije));
					listaTipovaProj.add(tip);
				}
				
				sala = new Sala(id,naziv,listaTipovaProj); 
			} else {
				
			}
		} catch(Exception e) {
			System.out.println("Ne postoji sala"); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return sala;
	}

	public static ArrayList<String> getSale() {
		ArrayList<String> sale = new ArrayList<String>(); 
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null;
		
		try {
			String query = "SELECT Naziv FROM Sale WHERE 1"; 
			
			prep = conn.prepareStatement(query); 
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String naziv = rs.getString(index++); 
				sale.add(naziv); 
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Ne mogu se ucitati sale"); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
		
		return sale;
	}

	public static ArrayList<JSONObject> getSaleProjekcije() {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null;
		
		ArrayList<JSONObject> listaSala = new ArrayList<JSONObject>();
		
		try {
			String query = "SELECT ID,Naziv,ID_Tipova_Projekcija FROM Sale WHERE 1";

			prep = conn.prepareStatement(query); 
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				int id = Integer.valueOf(rs.getString(index++));
				String naziv = rs.getString(index++);
				String[] tipoviProjekcijaID = rs.getString(index++).split(";");
				ArrayList<TipProjekcije> listaProjekcija = new ArrayList<TipProjekcije>();
				for (String string : tipoviProjekcijaID) {
					TipProjekcije tip = TipProjekcijeDAO.getTipProjekcijeObjectById(Integer.valueOf(string));
					if(tip!=null) {
						listaProjekcija.add(tip);
					}
				}
				
				Sala sala = new Sala(id, naziv, listaProjekcija);
				JSONObject odg = new JSONObject();
				odg.put("ID", sala.getId());
				odg.put("Naziv",sala.getNaziv());
				odg.put("MaksimumSedista",maksimalnoSjedistaSale(String.valueOf(sala.getId())));
				
				ArrayList<JSONObject> tipovi = new ArrayList<JSONObject>();
				for (TipProjekcije tipProjekacije : sala.getTipProjekcije()){
					JSONObject t = new JSONObject();
					t.put("ID", tipProjekacije.getId());
					t.put("Naziv",tipProjekacije.getNaziv());
					tipovi.add(t);
				}
				odg.put("listaTipova",tipovi);
				listaSala.add(odg);
			}
		} catch(Exception e) {
			System.out.println("Nije se ucitala sala u projekciju");
		}
		
		 finally {
				try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
			}
			
		return listaSala;
	}
	
	public static int maksimalnoSjedistaSale(String idSale) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null; 
		ResultSet rs= null; 
		int maksimumSjedistaSale = 0; 
		try {
			String query = "SELECT * FROM Sedista WHERE ID_Sale = ?";
			prep = conn.prepareStatement(query);
			prep.setString(1, idSale);
			
			rs= prep.executeQuery();
			while(rs.next()) {
				maksimumSjedistaSale++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		
		return maksimumSjedistaSale; 
	}

	public static ArrayList<JSONObject> getSaleSaProjekcijama() {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		ArrayList<JSONObject> listaSala = new ArrayList<JSONObject>(); 
		try {
			String query = "SELECT ID,Naziv,ID_Tipova_Projekcija FROM Sale WHERE 1";

			prep = conn.prepareStatement(query); 
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				int id = Integer.valueOf(rs.getString(index++)); 
				String naziv = rs.getString(index++); 
				String[] tipoviProjekcija_id = rs.getString(index++).split(";"); 
				
				ArrayList<TipProjekcije> listaProjekcija = new ArrayList<TipProjekcije>(); 
				for(String string : tipoviProjekcija_id) {
					TipProjekcije tip = TipProjekcijeDAO.getTipProjekcijeObjectById(Integer.valueOf(string)); 
					listaProjekcija.add(tip); 
				}
				Sala sala = new Sala(id,naziv,listaProjekcija); 
				JSONObject response = new JSONObject(); 
				response.put("ID", sala.getId()); 
				response.put("Naziv", sala.getNaziv()); 
				
				
				ArrayList<JSONObject> tipovi = new ArrayList<JSONObject>(); 
				for(TipProjekcije tipProjekcije : sala.getTipProjekcije()) {
					JSONObject tip = new JSONObject(); 
					tip.put("ID", tipProjekcije.getId()); 
					tip.put("Naziv", tipProjekcije.getNaziv()); 
					tipovi.add(tip); 
				}
				response.put("listaTipova", tipovi); 
				listaSala.add(response); 
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako s
		}
		
		return listaSala;
	}
	
	public static ArrayList<Sjediste> sjedistaSale(String idSale) {
		Connection connection = ConnectionManager.getConnection(); 
		PreparedStatement prep = null;
		ResultSet rs = null; 
		Sjediste sjediste = new Sjediste();
		
		ArrayList<Sjediste> listaSjedista = new ArrayList<Sjediste>(); 
		
		try {
			String query = "SELECT ID,ID_Sale,Broj_Sedista FROM Sedista WHERE ID_Sale=?";
			
			prep = connection.prepareStatement(query); 
			prep.setString(1, idSale);
			
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index =  1; 
				String id = rs.getString(index++); 
				String idS = rs.getString(index++); 
				String brojSjedista = rs.getString(index++); 
				sjediste = new Sjediste(Integer.valueOf(idS), Integer.valueOf(brojSjedista));
				listaSjedista.add(sjediste); 
			}
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {connection.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return listaSjedista;
	}
	
	public static ArrayList<Sjediste> slobodnaSjedistaSale(String idProjekcije) {
		Connection connection = ConnectionManager.getConnection(); 
		PreparedStatement prep = null ; 
		ResultSet rs = null ;
		Projekcija projekcija = new Projekcija(); 
		Sala sala = new Sala(); 
		
		ArrayList<Sjediste> listaSjedista = new ArrayList<Sjediste>();
		try {
			
			String query = "SELECT ID FROM Projekcije WHERE ID=?";
			
			prep = connection.prepareStatement(query); 
			prep.setString(1, idProjekcije);
			
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1;
				String id = rs.getString(index++); 
				projekcija = ProjekcijeDAO.getProjekcijaById(Integer.valueOf(id)); 
				
				int idSale = projekcija.getSalaId(); 
				
				//hocu listu sjedista i listu karata 
				ArrayList<Karta> listaKarataZaProjekciju = KarteDAO.listaKarataZaProjekciju(idProjekcije);
				ArrayList<Sjediste> listaSjedistaZaProjekciju = SalaDAO.sjedistaSale(String.valueOf(idSale));
				
				//Sjediste jednoSjediste; 
				//Karta jednaKarta;
				for(Sjediste jednoSjediste : listaSjedistaZaProjekciju) {
					for(Karta jednaKarta : listaKarataZaProjekciju) {
						if((jednoSjediste.getRedniBroj()+"").equals(jednaKarta.getOznakaSjedista())) {
							System.out.println("Nije slobondo");
						}
					} 
					listaSjedista.add(jednoSjediste);
				}
				
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		 finally {
				try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {connection.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
			}
		return listaSjedista;
	}

	public static Sjediste getSjedisteObjectById(String idSjedista) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		Sjediste sjediste = new Sjediste(); 
		
		try {
			String query = "SELECT ID,ID_Sale,Broj_Sedista FROM Sedista WHERE ID=?";
			
			prep = conn.prepareStatement(query); 
			prep.setString(1, idSjedista);
			
			rs = prep.executeQuery(); 
			
			if(rs.next()) {
				int index = 1; 
				String id = rs.getString(index++); 
				String idSale = rs.getString(index++); 
				String brojSjedista = rs.getString(index++); 
				
				sjediste = new Sjediste(Integer.valueOf(idSale), Integer.valueOf(brojSjedista)); 
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return sjediste;
	}
	
	public static String getSjedisteId(String idSale, String redniBrojSjedista) {
		String broj = "0";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT ID FROM Sedista WHERE ID_Sale=? AND Broj_Sedista=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,idSale);
			pstmt.setString(2, redniBrojSjedista);

			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				int index = 1;
				String ajdi = rset.getString(index++);
				broj = ajdi;
			}
			
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
		try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
		try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
	}
		return broj;
	}
	
	
	
	
	
	
	
	
	
	
}
