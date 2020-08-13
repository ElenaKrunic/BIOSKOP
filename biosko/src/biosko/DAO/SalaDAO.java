package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.ws.Response;

import org.json.simple.JSONObject;

import bioskop.model.Sala;
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
	/*

	public static Sala getSalaObjectById(int salaId) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null;
		
		Sala sala = null; 

		try {
			String query = "SELECT ID,Naziv,ID_Tipova_Projekcija FROM Sale WHERE ID = ?";
			
			prep= conn.prepareStatement(query); 
			prep.setString(1, String.valueOf(salaId));
			
			rs= prep.executeQuery(); 
			
			if(rs.next()) {
				int index = 1; 
				int ID = Integer.valueOf(rs.getString(index++)); 
				String Naziv = rs.getString(index++); 
				String[] tipoviProjekcijaID = rs.getString(index++).split(";");
				ArrayList<TipProjekcije> listaTipovaProjekcije =  new ArrayList<TipProjekcije>();
				
				for(String string : tipoviProjekcijaID) {
					TipProjekcije tip = TipProjekcijeDAO.getTipProjekcijeObject(Integer.valueOf(string)); 
					if(tip != null) {
						listaTipovaProjekcije.add(tip); 
					} else {
						System.out.println("Tip projekcije sa tim IDjem ne postoji"); 
					}
				}
				sala = new Sala(ID,Naziv, listaTipovaProjekcije);
			} 
			else {
				System.out.println("Nije vracena sala sa projekcijama"); 
			}
		} catch(Exception e) {
			System.out.println("Ne moze se pronaci sala sa tim id-jem"); 
		}
		
		 finally {
				try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		 }
		
		return sala;
	}
*/

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
				odg.put("MaksimumSedista",brojMaksimumSedistaSale(String.valueOf(sala.getId())));
				
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
	public static int brojMaksimumSedistaSale(String idSale) throws SQLException{
		int broj = 0;
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Sala sala = null;
		try {
			String query = "SELECT * FROM Sedista WHERE ID_Sale = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, String.valueOf(idSale));

			rset = pstmt.executeQuery();
			while(rset.next()) {
				broj++;
			}

		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}

		return broj;
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
}
