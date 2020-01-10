package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bioskop.model.Korisnik;
import bioskop.model.Uloga;
import biosko.DAO.ConnectionManager;

//uloga toString jer baza ne prepoznaje enum

//ovde radim update delete edit
public class KorisnikDAO {
	
	public static List<Korisnik> getAll(String ime, Uloga uloga) throws Exception {
		return new ArrayList<>();
	}

	public static Korisnik get(String userName, String password) throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT FROM korisnici WHERE userName = ?, password = ?, uloga = ?";
			prep = conn.prepareStatement(query);
			int index = 1; 	
			prep.setString(index++, userName);
			prep.setString(index++, password);
			System.out.println(prep);
			
			rs = prep.executeQuery();
			if(rs.next()) { //sl red
				Uloga uloga = Uloga.valueOf(rs.getString(1)); //column index
				return new Korisnik(userName,password,uloga);
			}
		}
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
		return null;
	}	
	
	public static Korisnik get(String userName) throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT password,uloga FROM korisnici WHERE userName = ?";
			prep = conn.prepareStatement(query);
			prep.setString(1, userName);
			System.out.println(prep);
			
			rs = prep.executeQuery();
			
			if(rs.next()) { //sl red
				int index = 1;
				String password = rs.getString(index++);
				Uloga uloga = Uloga.valueOf(rs.getString(index++)); //column index
				return new Korisnik(userName,password,uloga);
			}
		}
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
		return null;
	}	
	
	//delete update edit 
	//ogledaj u webshop update
	
	public static boolean dodajKorisnika(Korisnik korisnik) throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		try {
			String query = "INSERT INTO korisnici (userName,password,uloga)" + "VALUES (?,?,?)";
			prep = conn.prepareStatement(query);
			
			int index = 1; 
			prep.setString(index++, korisnik.getKorisnickoIme());
			prep.setString(index++, korisnik.getLozinka());
			prep.setString(index++, korisnik.getUloga().toString()); //jer baza ne prepoznaje enum
			
			System.out.println(prep);
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
			String query = "UPDATE korisnici SET userName = ?, password = ?, role = ?";
			prep = conn.prepareStatement(query);
			
			int index = 1;
			prep.setString(index++, korisnik.getKorisnickoIme());
			prep.setString(index++, korisnik.getLozinka());
			prep.setString(index++, korisnik.getUloga().toString());
			
			System.out.println(prep);
			
			return prep.executeUpdate() == 1;
		} finally {
			try {prep.close();} catch(Exception ec) {ec.printStackTrace();}
			try {prep.close();} catch(Exception ec) {ec.printStackTrace();}
		}
	}
	
	public static boolean obrisiKorisnika(Korisnik korisnik) throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		
		try {
			String query = "DELETE FROM korisnici WHERE userName = ?, password = ?, role = ?";
			prep = conn.prepareStatement(query);
			
			int index = 1; 
			prep.setString(index++, korisnik.getKorisnickoIme());
			prep.setString(index++, korisnik.getLozinka());
			prep.setString(index++, korisnik.getUloga().toString());
			
			System.out.println(prep);
			
			return prep.executeUpdate() == 1;
		} finally {
			try {prep.close();} catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();} catch(Exception ex) {ex.printStackTrace();}
		}
	
	}
	
}
