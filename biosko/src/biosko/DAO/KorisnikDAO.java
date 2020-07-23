package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import bioskop.model.Korisnik;
import bioskop.model.Uloga;
import biosko.DAO.ConnectionManager;

public class KorisnikDAO {
	
	//dobavljanje korisnika preko ID-a 
		//provjeriti da li sam sve dobro upisala u bazu 
		public static Korisnik get(String id) {
			//dobavljanje konekcije preko putanje 
			Connection conn = ConnectionManager.getConnection();
			//inicijalizacija preparedstmn, resulta i korisnika 
			PreparedStatement prep = null; 
			ResultSet rs = null; 
			Korisnik korisnik = null; 
			try {
				//probaj da izvrsis ovaj query 
				String query = "SELECT ID, Username,Password,DatumRegistracije,Uloga,Status FROM Users"
						+ " WHERE ID = ?";
				
				//konekcija ima bazu, znaci pozivam bazu i prosljedjujem joj zadatak 
				//koji treba da izvrsi 
				//to sve smjestaj u prepared statement 
				prep = conn.prepareStatement(query); 
				//setujem string na 1
				//zasto sam setovala string 1? 
				prep.setString(1, id);
				
				//result set sluzi za izvrsavanje queryja 
				rs = prep.executeQuery(); 
				
				if(rs.next()) {
					//ovo se ovako pise 
					int index = 1; 
				
					String ID = rs.getString(index++);
					String Username = rs.getString(index++); 
					String Password = rs.getString(index++); 
					String Datum = rs.getString(index++); 
					String Uloga = rs.getString(index++); 
					String Status = rs.getString(index++); 
					
					//date format ima vrijednost yyyy mm dd 
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					//pravim objekat datum i dodjeljujem mu format 
					//format ima obicnu formu i on forsira Datum objekat iz rs 
					Date d = format.parse(Datum);
					
					//sve ove vrijednosti dodjeljujemo novom korisniku kojeg sam gore inicijalizovala 
					korisnik = new Korisnik(ID, Username,Password,bioskop.model.Uloga.valueOf(Uloga),d,Status); 
				}
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			finally {
				try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
			}
			return korisnik; 
		}

		@SuppressWarnings("unchecked")
		public static JSONObject loadAllUsers(String username, String password, String date,String type) {
			JSONObject response = new JSONObject(); 
			ArrayList<JSONObject> korisnici = new ArrayList<JSONObject>(); 
			
			Connection conn = ConnectionManager.getConnection(); 
			PreparedStatement prep = null; 
			ResultSet rs = null;
			boolean status = false; 
			
			try {
				String query = "SELECT ID, Username,Password,DatumRegistracije,Uloga,Status FROM Users"
						+ " WHERE Username LIKE ? AND Password LIKE ? AND DatumRegistracije LIKE ? AND Uloga LIKE ?";
				
				prep = conn.prepareStatement(query);
				prep.setString(1, "%"+username+"%");
				prep.setString(2, "%"+password+"%");
				prep.setString(3,"%"+date+"%");
				prep.setString(4,"%"+type+"%");
				
				rs = prep.executeQuery();
				
				while(rs.next()) {
					status = true; 
					
					int index = 1; 
					String ID = rs.getString(index++); 
					String Username = rs.getString(index++); 
					String Password = rs.getString(index++); 
					String Datum = rs.getString(index++); 
					String Uloga = rs.getString(index++); 
					String Status = rs.getString(index++); 
					
					DateFormat f = new SimpleDateFormat("yyyy-MM-dd"); 
					Date d = f.parse(Datum); 
					
					Korisnik korisnik = new Korisnik(ID,Username,Password,bioskop.model.Uloga.valueOf(Uloga),d,Status);
					JSONObject uobject = new JSONObject(); 
					uobject.put("ID", korisnik.getID()); 
					uobject.put("Username", korisnik.getKorisnickoIme()); 
					uobject.put("Password",korisnik.getLozinka());
					uobject.put("Datum", f.format(korisnik.getDatumReg()));
					uobject.put("Uloga", korisnik.getUloga().toString()); 
					uobject.put("Status", korisnik.getStatus()); 
					korisnici.add(uobject);
				}
				
				response.put("status", status); 
				response.put("lista", korisnici);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			finally {
				try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
			}
			
			return response; 
		}

		//@SuppressWarnings("unchecked")
		public static JSONObject register(HttpServletRequest request) {
			JSONObject response = new JSONObject();
			
			boolean status = false; 
			String poruka = "Doslo je do nepredvidjenje greske!"; 
			String username = request.getParameter("username"); 
			String password = request.getParameter("password"); 
			System.out.println("U register metodi sam : " + username + " " + password); 
			if(username.length() == 0 || password.length() == 0) {
				response.put("status", false); 
				poruka = "Nepravilan unos! ";
				response.put("poruka", poruka); 
				return response; 
			} else {};
			if(existsUser(username)) {
				response.put("status", false); 
				poruka = "Korisnicko ime je u upotrebi!"; 
				response.put("poruka", poruka); 
				return response; 
			}
			else {
				Connection conn = ConnectionManager.getConnection(); 
				PreparedStatement prep = null; 
				
				try {
					String query = "INSERT INTO Users(Username,Password,DatumRegistracije,Uloga,Status) VALUES (?,?,?,'obicanKorisnik','Active')";
					
					prep = conn.prepareStatement(query); 
					prep.setString(1, username);
					prep.setString(2,password);
					DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date(); 
					prep.setString(3, f.format(date));
					
					int b = prep.executeUpdate(); 
					if(b>0) {
						status = true; 
						poruka = "Uspjesno ste se registrovali!"; 
						request.getSession().setAttribute("username",username); 
						request.getSession().setAttribute("password", password);
						request.getSession().setAttribute("uloga", "obicanKorisnik");
						request.getSession().setAttribute("status", "Active");
						request.getSession().setAttribute("ID", String.valueOf(ucitajBrojKorisnika()));
					}
					else {
						poruka = "Neuspjesan unos u bazu!"; 
					}
				}
				 catch (Exception e) {
						e.printStackTrace();
				}
				finally {
					try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
					try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
				}
				response.put("status",status);
				response.put("poruka",poruka);
				return response;
			}
		}
		
		public static int ucitajBrojKorisnika() {
			int broj = 0;
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			
			try {
				String query = "SELECT ID FROM Users WHERE 1";
				pstmt = conn.prepareStatement(query);
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					broj++;
				}
		}
		catch(Exception e) {
			
		}
		finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
			return broj;
		}

		private static boolean existsUser(String username) {
			Boolean status  = true; 
			
			Connection conn = ConnectionManager.getConnection(); 
			PreparedStatement prep = null ; 
			ResultSet rs = null; 
			
			try {
				String query = "SELECT ID,Username,Password,DatumRegistracije,Uloga,Status FROM Users"
						+ " WHERE Username = ?";
				
				prep = conn.prepareStatement(query); 
				prep.setString(1, username);
				
				rs = prep.executeQuery(); 
				
				if(!rs.next()) {
					status = false; 
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return false;
		}
		
		public static JSONObject login(HttpServletRequest request) {
			//inicijalizacija 
			JSONObject response = new JSONObject(); 
			String poruka = ""; 
			Korisnik korisnik = null; 
			boolean status = false;
			JSONObject jsonKorisnik = null; 
			String username = request.getParameter("userName"); 
			String password = request.getParameter("password"); 
			Connection conn = ConnectionManager.getConnection(); 
			PreparedStatement prep = null; 
			ResultSet rs = null; 
			
			try {
				String query = "SELECT ID, Username,Password,DatumRegistracije,Uloga,Status FROM Users WHERE Username=? AND Password=? AND Status='Active'";
				
				prep = conn.prepareStatement(query); 
				prep.setString(1, username);
				prep.setString(2, password);
				
				rs = prep.executeQuery();
				
				if(rs.next()) {
					int index = 1; 
					String ID = rs.getString(index++); 
					String Username = rs.getString(index++); 
					String Password = rs.getString(index++); 
					String Datum = rs.getString(index++); 
					String Uloga = rs.getString(index++); 
					String Status = rs.getString(index++); 
					
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
					Date d = format.parse(Datum); 
					
					korisnik = new Korisnik(ID, Username,Password,bioskop.model.Uloga.valueOf(Uloga),d,Status); 
					
					jsonKorisnik = new JSONObject(); 
					
					//ispis u konzoli,pored poruke 
					jsonKorisnik.put("userName", korisnik.getKorisnickoIme()); 
					jsonKorisnik.put("uloga", korisnik.getUloga().toString()); 
					jsonKorisnik.put("status", korisnik.getStatus());
					status = true; 
					request.getSession().setAttribute("userName", korisnik.getKorisnickoIme());
					request.getSession().setAttribute("password", korisnik.getLozinka());
					request.getSession().setAttribute("uloga", korisnik.getUloga().toString());
					request.getSession().setAttribute("status", korisnik.getStatus());
					request.getSession().setAttribute("ID", String.valueOf(korisnik.getID()));
					
					poruka = "Uspjesno ste se ulogovali!! Cestitamo!"; 
				}
				else { 
					poruka = "Logovanje nije uspjelo!"; 
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			finally {
				try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
			}
			
			//response.put("status",status); 
			//response.put("korisnik",korisnik);
			response.put("status", status); 
			response.put("jsonKorisnik",jsonKorisnik);
			response.put("poruka",poruka); 
			
			return response; 		
		}
	
	/*
	
	public static Korisnik get(String korisnickoIme, String lozinka) throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT uloga,datumRegistracije FROM korisnici WHERE korisnickoIme = ? AND lozinka = ?";
			prep = conn.prepareStatement(query);
			int index = 1; 	
			prep.setString(index++, korisnickoIme);
			prep.setString(index++, lozinka);
			
			rs = prep.executeQuery();
			
			if(rs.next()) {
				Uloga uloga = Uloga.valueOf(rs.getString(1)); //column index
				String datumRegistracije = rs.getString("datumRegistracije"); 
				return new Korisnik(korisnickoIme,lozinka,datumRegistracije,uloga);
			}
		}
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
		return null;
	}	
	
	public static Korisnik get(String korisnickoIme) throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT lozinka,datumRegistracije, uloga FROM korisnici WHERE korisnickoIme = ?";
			prep = conn.prepareStatement(query);
			prep.setString(1, korisnickoIme);
			System.out.println(prep);
			
			rs = prep.executeQuery();
			
			if(rs.next()) { //sl red
				int index = 1;
				String lozinka = rs.getString(index++);
				String datumRegistracije = rs.getString(index++); 
				Uloga uloga = Uloga.valueOf(rs.getString(index++)); //column index
				return new Korisnik(korisnickoIme,datumRegistracije,lozinka,uloga);
			}
		}
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
		return null;
	}	
	
	public static List<Korisnik> getAll(String korisnickoIme, Uloga uloga) throws Exception {
		List<Korisnik> korisnici = new ArrayList<>(); 
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		try {
			String query = "SELECT * FROM korisnici WHERE korisnickoIme LIKE ? AND uloga LIKE ?"; 
			prep = conn.prepareStatement(query);
			int index = 1; 
			
			prep.setString(index++, "%" + korisnickoIme + "%");
			prep.setString(index++, "%" + uloga + "%");
			
			rs = prep.executeQuery();
			
			while(rs.next()) {
				String korisnickoImeKorisnikk = rs.getString("korisnickoIme"); 
				String lozinka = rs.getString("lozinka"); 
				String datumRegistracije = rs.getString("datumRegistracije"); 
				Uloga ulogaa = Uloga.valueOf(rs.getString("uloga")); 
				
				Korisnik korisnik = new Korisnik(korisnickoImeKorisnikk,lozinka,datumRegistracije,ulogaa); 
				korisnici.add(korisnik);
			}
		} finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		} 		
		return korisnici;
	}
	
	
	public static boolean dodajKorisnika(Korisnik korisnik) throws SQLException, Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		try {
			String query = "INSERT INTO korisnici (korisnickoIme,lozinka,datumRegistracije,uloga)" + "VALUES (?,?,?,?)";
			prep = conn.prepareStatement(query);
			int index = 1; 
			prep.setString(index++, korisnik.getKorisnickoIme());
			prep.setString(index++, korisnik.getLozinka());
			prep.setString(index++, korisnik.getDatumRegistracije());
			prep.setString(index++, korisnik.getUloga().toString()); //jer baza ne prepoznaje enum
			
			return prep.executeUpdate() == 1;
			
		}
		finally {
			try {prep.close();} catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();} catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	public static boolean izmijeniKorisnika(Korisnik korisnik) throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		try {
			String query = "UPDATE korisnici SET korisnickoIme LIKE ? , lozinka LIKE ?, uloga LIKE ?";
			prep = conn.prepareStatement(query);
			
			int index = 1;
			prep.setString(index++, korisnik.getKorisnickoIme());
			prep.setString(index++, korisnik.getLozinka());
			prep.setString(index++, korisnik.getDatumRegistracije());
			prep.setString(index++, korisnik.getUloga().toString());
			
			System.out.println(prep);
			
			return prep.executeUpdate() == 1;
		} finally {
			try {prep.close();} catch(Exception ec) {ec.printStackTrace();}
			try {prep.close();} catch(Exception ec) {ec.printStackTrace();}
		}
	}
	
	public static boolean obrisiKorisnika(Korisnik korisnik) throws SQLException, Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		
		try {
			String query = "DELETE FROM korisnici WHERE korisnickoIme = ? , lozinka = ?, uloga = ?";
			prep = conn.prepareStatement(query);
			
			int index = 1; 
			prep.setString(index++, korisnik.getKorisnickoIme());
			prep.setString(index++, korisnik.getLozinka());
			prep.setString(index++, korisnik.getDatumRegistracije());
			prep.setString(index++, korisnik.getUloga().toString());
			
			System.out.println(prep);
			
			return prep.executeUpdate() == 1;
		} finally {
			try {prep.close();} catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();} catch(Exception ex) {ex.printStackTrace();}
		}
	
	}
	
	*/
	
}
