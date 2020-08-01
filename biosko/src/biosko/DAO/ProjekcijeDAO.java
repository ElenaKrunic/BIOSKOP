package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import bioskop.model.Projekcija;
import bioskop.model.TipProjekcije;

public class ProjekcijeDAO {
	
	public static ArrayList<Projekcija> ucitajProjekcijuZaDanasnjiDatum(HttpServletRequest request, String datum ) {
		ArrayList<Projekcija> listaProjekcija = new ArrayList<Projekcija>();
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		try {
			String query = "SELECT ID FROM Projekcije  WHERE Status='Active' AND Termin BETWEEN ? AND ? ORDER BY ID_Filma ASC,Termin ASC ;";
			
			prep = conn.prepareStatement(query); 
			prep.setString(1, datum);
			prep.setString(2, datum+" 23:59:59");
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String id = rs.getString(index++); 
				Projekcija projekcija = getProjekcijaById(Integer.valueOf(id)); 
				listaProjekcija.add(projekcija); 
			}
		} catch(Exception e) {
			System.out.println("Ne postoje projekcije za danasnji dan"); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return listaProjekcija; 
	}
	
	public static Projekcija getProjekcijaById(int id) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		Projekcija projekcija = null; 
		
		try {
			String query = "SELECT ID,ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata FROM Projekcije  WHERE ID=?;";
			
			prep = conn.prepareStatement(query); 
			prep.setInt(1, id);
			rs = prep.executeQuery(); 
			
			if(rs.next()) {
				int index = 1; 
				String idProjekcije = rs.getString(index++); 
				int idP = Integer.valueOf(idProjekcije);
				String idFilma = rs.getString(index++); 
				int idF = Integer.valueOf(idFilma);
				String tipProjekcije = rs.getString(index++); 
				String idSale = rs.getString(index++); 
				int idS = Integer.valueOf(idSale); 
				String termin = rs.getString(index++); 
				Date praviTermin = new SimpleDateFormat("yyyy-MM-dd").parse(termin);
				String cijenaKarte = rs.getString(index++); 
				int pravaCijena = Integer.valueOf(cijenaKarte); 
				String admin = rs.getString(index++); 
				String status = rs.getString(index++); 
				int maksKarata = Integer.valueOf(rs.getString(index++)); 
				int prodanoKarata = Integer.valueOf(rs.getString(index++)); 
				projekcija = new Projekcija(idP,idF,tipProjekcije,idS,praviTermin,pravaCijena,
						admin,status,maksKarata,prodanoKarata);
			} else {
				System.out.println("Ne postoji projekcija sa datim info"); 
			}
		} catch(Exception e) {
			System.out.println("Ne postoji projekcija sa datim ID-jem"); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return projekcija; 
	}

	public static JSONObject getById(String projekcijaId) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		try {
			String query = "SELECT ID,ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata FROM Projekcije  WHERE ID=?;";
			
			prep = conn.prepareStatement(query); 
			prep.setString(1, projekcijaId);
			
			rs = prep.executeQuery(); 
			
			if(rs.next()) {
				int index = 1; 
				int ID = Integer.valueOf(rs.getString(index++)); 
				int ID_Filma = Integer.valueOf(rs.getString(index++)); 
				String Tipovi_Projekcija = rs.getString(index++); 	
				int ID_Sale = Integer.valueOf(rs.getString(index++)); 
				String Termin = rs.getString(index++); 
				int CenaKarte = Integer.valueOf(rs.getString(index++)); 
				String Administrator = rs.getString(index++); 
				String Status = rs.getString(index++); 
				int MaksimumKarata = Integer.valueOf(rs.getString(index++)); 
				int BrojProdanihKarata = Integer.valueOf(rs.getString(index++)); 
				String KrajTermina = rs.getString(index++); 
				
			} else {
				
			}
		} catch(Exception e) {
			System.out.println("Ne moze se ucita projekcija sa tim id-jem"); 
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	public static ArrayList<Projekcija> ucitajZaDanasnjiDatum(HttpServletRequest request, String danasnjiDatum) {
		ArrayList<Projekcija> lista = new ArrayList<Projekcija>(); 
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		boolean status= false; 
		try {
			String query = "SELECT ID FROM Projekcije  WHERE Status='Active' AND Termin BETWEEN ? AND ? ORDER BY ID_Filma ASC,Termin ASC ;";
			prep = conn.prepareStatement(query); 
			
			prep.setString(1,danasnjiDatum);
			prep.setString(2, danasnjiDatum+" 23:59:59");
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String id = rs.getString(index++); 
				Projekcija projekcija = get(Integer.valueOf(id)); 
				if(projekcija !=null) {
					lista.add(projekcija);
					status = true; 
				}
				//lista.add(projekcija);
			}
		} catch(Exception e) {
			System.out.println("Ne postoji projekcija sa datim ID-jem"); 
		}
		 finally {
				try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		 }
		return lista;
	}
	*/
	
	/*
	
	public static ArrayList<Projekcija> ucitajZaDatum(HttpServletRequest request,String datum) {
		ArrayList<Projekcija> lista = new ArrayList<Projekcija>();
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		boolean status = false;
		try {
			String query = "SELECT ID FROM Projekcije  WHERE Status='Active' AND Termin BETWEEN ? AND ? ORDER BY ID_Filma ASC,Termin ASC ;";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, datum);
			pstmt.setString(2,datum+" 23:59:59");
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int index = 1;
				String ID = rset.getString(index++);
				Projekcija p = get(Integer.valueOf(ID));
				if(p!=null) {
					lista.add(p);
				}
				
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
		return lista;
	}	
	
	public static Projekcija get(int id) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		boolean status = false;
		Projekcija proj = null;
		try {
			String query = "SELECT ID,ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata FROM Projekcije  WHERE ID=?;";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				String ID1 = rset.getString(index++);
				int idi = Integer.valueOf(ID1);
				String ID_Filma = rset.getString(index++);
				int idFilma = Integer.valueOf(ID_Filma);
				String tipProjekcije = rset.getString(index++);
				String ID_Sale = rset.getString(index++);
				int idSale = Integer.valueOf(ID_Sale);
				String Termin = rset.getString(index++);
				Date datum = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(Termin);
				String CenaKarte = rset.getString(index++);
				int cenaKarte = Integer.valueOf(CenaKarte);
				String Administrator = rset.getString(index++);
				String Status = rset.getString(index++);
				int maks = Integer.valueOf(rset.getString(index++));
				int prod = Integer.valueOf(rset.getString(index++));
				proj = new Projekcija(idi, idFilma, tipProjekcije, idSale, datum, cenaKarte, Administrator,Status,maks,prod);
				status = true;
			}
			else {
				System.out.println("Nisam nasao projekciju");
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
		return proj;
	}

	/*
	private static Projekcija getProjekcijaObjectById(int id) {
		Connection connection = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		Projekcija projekcija = null; 
		boolean status = false; 
		
		try {
			String query = "SELECT ID,ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata FROM Projekcije  WHERE ID=?;";
			
			prep = connection.prepareStatement(query);
			prep.setInt(1, id);
			rs = prep.executeQuery(); 
			
			if(rs.next()) {
				int index = 1; 
				String ID = rs.getString(index++); 
				int praviID = Integer.valueOf(ID); 
				String filmID = rs.getString(index++); 
				int filmPraviID = Integer.valueOf(filmID); 
				String tipProjekcije = rs.getString(index++); 
				String idSale = rs.getString(index++);
				int praviIdSale = Integer.valueOf(idSale); 
				String termin = rs.getString(index++); 
				Date praviTermin = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(termin);
				String cijenaKarte = rs.getString(index++); 
				int pravaCijenaKarte = Integer.valueOf(cijenaKarte); 
				String admin = rs.getString(index++); 
				String statusProjekcije = rs.getString(index++); 
				int maksimalanBrojKarata = Integer.valueOf(rs.getString(index++)); 
				int brojProdatihKarata = Integer.valueOf(rs.getString(index++)); 
				projekcija = new Projekcija(praviID,filmPraviID,tipProjekcije,praviIdSale,praviTermin,pravaCijenaKarte,admin,statusProjekcije,
						maksimalanBrojKarata,brojProdatihKarata); 
				
			} else {
				System.out.println("Ne postoji projekcija");
			}
		} catch(Exception e) {
			//System.out.println("Ne postoji projekcija");
			e.printStackTrace();
		}
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {connection.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return projekcija;
	}
*/
}
