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
						request.getSession().setAttribute("userName",username); 
						request.getSession().setAttribute("password", password);
						request.getSession().setAttribute("uloga", "obicanKorisnik");
						request.getSession().setAttribute("status", "Active");
						request.getSession().setAttribute("ID", String.valueOf(ucitajIDKorisnika()));
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
		
		public static int ucitajIDKorisnika() {
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

		public static JSONObject getKorisnikInfo(HttpServletRequest request) {
			JSONObject response = new JSONObject(); 
			String username = (String) request.getSession().getAttribute("userName");
			System.out.println("Korisnicko ime iz  sesije je " + username);
			String password = (String) request.getSession().getAttribute("password"); 
			String uloga = (String) request.getSession().getAttribute("uloga"); 
			String status = (String) request.getSession().getAttribute("status"); 
			String id = (String) request.getSession().getAttribute("ID"); 
			
			response.put("status",status); 
			response.put("userName", username); 			
			response.put("password",password); 
			response.put("uloga",uloga);
			response.put("ID",id); 
			
			return response;
		}
		
		public static boolean validUserInfo(String username, String password) {
			boolean ulogovan = false; 
			
			Connection conn = ConnectionManager.getConnection(); 
			PreparedStatement prep = null; 
			ResultSet rs = null; 
			
			try {
				String query = "SELECT ID, Username,Password,DatumRegistracije,Uloga,Status FROM Users WHERE Username=? AND Password=? AND Status='Active'";
				
				prep = conn.prepareStatement(query); 
				prep.setString(1,username);
				prep.setString(2, password);
				
				rs = prep.executeQuery(); 
				
				if(rs.next()) {
					ulogovan = true;
				}
			} catch(Exception e) {
				
			}
			
			finally {
				try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
			}
			return ulogovan; 
		}
}
