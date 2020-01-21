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


public class KorisnikDAO {
	
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
	
}
