package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
			String query = "SELECT ID FROM Projekcije  WHERE Status='Active' AND Termin BETWEEN ? AND ?";
			
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
			
			//System.out.println("Lista projekcija za danas su " + listaProjekcija); 
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
				//OVDE MI JE BILA GRESKA, NISAM DODALA HH:mm!!!!!!!!!!!!
				Date praviTermin = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(termin);
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

	public static JSONObject getProjekcije(String idFilm, String pocetakProjekcije,
			String zavrsetakProjekcije, String salaProjekcije, String tipP, String najmanjaCijena,
			String najvecaCijena) {
		
		JSONObject response = new JSONObject(); 
		ArrayList<Projekcija> lista = new ArrayList<Projekcija>(); 
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		try {
			String query = "SELECT ID FROM Projekcije WHERE Status='Active' AND (Termin BETWEEN ? AND ?) AND ID_Filma LIKE ?"
					+ " AND ID_sale LIKE ? AND TipProjekcije LIKE ? AND CenaKarte BETWEEN ? AND ?  ORDER BY ID_Filma ASC,Termin ASC";
			
			prep = conn.prepareStatement(query); 
			
			prep.setString(1, pocetakProjekcije);
			prep.setString(2, zavrsetakProjekcije);
			prep.setString(3, idFilm);
			prep.setString(4, salaProjekcije);
			prep.setString(5, tipP);
			prep.setString(6, najmanjaCijena);
			prep.setString(7, najvecaCijena);
			
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String id = rs.getString(index++); 
				Projekcija projekcija = getProjekcijaById(Integer.valueOf(id));
				lista.add(projekcija);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		response.put("listaProjekcija",lista);
		return response;
	}

	public static ArrayList<String> getTipoviProjekcija() {
		ArrayList<String> tipoviProjekcija = new ArrayList<String>(); 
		
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		try {
			String query = "SELECT Naziv FROM Tipovi_Projekcija WHERE 1"; 
			
			prep = conn.prepareStatement(query);
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String naziv = rs.getString(index++); 
				tipoviProjekcija.add(naziv); 
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Nema tipa projekcije"); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return tipoviProjekcija;
	}

	public static JSONObject filterProjekcije(String idFilma, String pocetak, String kraj, String idSale,
			String tipProjekcije, String cijenaMin, String cijenaMax) {
		JSONObject odg = new JSONObject();
		boolean status = false;
		
		ArrayList<Projekcija> lista = new ArrayList<Projekcija>();
				
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				
				try {
					String query = "SELECT ID FROM Projekcije WHERE Status='Active' AND (Termin BETWEEN ? AND ?) AND ID_Filma LIKE ?"
							+ " AND ID_sale LIKE ? AND TipProjekcije LIKE ? AND CenaKarte BETWEEN ? AND ?  ORDER BY ID_Filma ASC,Termin ASC";
		
					pstmt = conn.prepareStatement(query);
		
					pstmt.setString(1,pocetak);
					pstmt.setString(2,kraj);
					pstmt.setString(3,idFilma);
					pstmt.setString(4,idSale);
					pstmt.setString(5,tipProjekcije);
					pstmt.setString(6, cijenaMin);
					pstmt.setString(7, cijenaMax);
					
					rset = pstmt.executeQuery();
					while(rset.next()) {
						
						int index = 1;
						String ID = rset.getString(index++);
						Projekcija p = get(Integer.valueOf(ID));
						//System.out.println("Projekcijae koje se filtriraju su " + p);
						if(p!=null) {
							lista.add(p);
						}
					status = true;
					}
					odg.put("listaProjekcija", lista);
					
					
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
			}
		
		odg.put("status", status);
		return odg;
	}

	private static Projekcija get(int id) {
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
				System.out.println("nema proj");
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
	
	public static boolean dodajProjekciju(Projekcija projekcija, String krajTermina) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		Date date = new Date(); 
		boolean status = false; 
		try {
			String query = "INSERT INTO Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,KrajTermina) VALUES (?,?,?,?,?,?,?,?,?)";
			
			prep = conn.prepareStatement(query); 
		
			
			prep.setString(1, projekcija.getFilmId() + "");
			prep.setString(2, projekcija.getTipProjekcije());
			prep.setString(3, projekcija.getSalaId() + "");  //"" jestring
			date = projekcija.getDatumPrikazivanje(); 
			String pocetak = dateFormat.format(date); 
			prep.setString(4, pocetak);
			prep.setString(5, projekcija.getCijenaKarte()+ "");
			prep.setString(6, projekcija.getAdminDodaoProjekciju());
			prep.setString(7, "Active");
			prep.setString(8, projekcija.getMaxKarata()+ " ");
			prep.setString(9, krajTermina);
			
			prep.executeUpdate(); 
			
			System.out.println("Izvrsena executeUpdate kod dodajProjekciju"); 
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		
		return status;
	}

	public static boolean obrisiProjekciju(String projekcijaId) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		boolean status = false; 
		
		try {
			String query = "UPDATE Projekcije SET Status='Deleted' WHERE ID=?";

			prep = conn.prepareStatement(query); 
			prep.setString(1, projekcijaId);
			prep.executeUpdate(); 
			
			System.out.println("Izvr executeUpdate u obrisiProjekciju"); 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return status;
	}

	public static ArrayList<Projekcija> ucitajProjekcijuZaFilm(String iD, String datum) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		ResultSet rs = null; 
		Projekcija projekcija = null;
		ArrayList<Projekcija> listaProjekcija = new ArrayList<Projekcija>();
		try {
			String query = "SELECT ID FROM Projekcije WHERE Status='Active' AND ID_Filma=? AND (Termin BETWEEN ? AND ?)ORDER BY ID;";
			
			prep = conn.prepareStatement(query); 
			prep.setString(1, iD);
			prep.setString(2, datum); //prvi
			prep.setString(3, datum + "23:59:59 "); 
			
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String id = rs.getString(index++); 
				
				projekcija = getProjekcijaById(Integer.valueOf(id));
				listaProjekcija.add(projekcija);
			}

			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return listaProjekcija;
	}


}
