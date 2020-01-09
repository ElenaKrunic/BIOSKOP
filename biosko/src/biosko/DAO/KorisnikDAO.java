package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bioskop.model.Korisnik;
import bioskop.model.Uloga;

//uloga toString jer baza ne prepoznaje enum

//ovde radim update delete edit
public class KorisnikDAO {
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
}
