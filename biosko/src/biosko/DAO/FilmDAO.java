package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bioskop.model.Film;
import bioskop.model.Zanrovi;


public class FilmDAO {
	public static Film get(int id) throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT naziv,zanr,trajanje,distributer,zemljaPorijekla,godinaProizvodnje FROM filmovi WHERE id = ?";
			prep = conn.prepareStatement(query);
			prep.setInt(1, id);
			System.out.println(prep);
			rs = prep.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				String naziv = rs.getString(index++);
				Zanrovi zanr = Zanrovi.valueOf(rs.getString(index++));
				String trajanje = rs.getString(index++);
				String distributer = rs.getString(index++); 
				String zemljaPorijekla = rs.getString(index++); 
				int godinaProizvodnje = rs.getInt(index++); 
				
				return new Film(id,naziv,zanr,trajanje,distributer,zemljaPorijekla,godinaProizvodnje); 
			}
		} finally {
			try {prep.close();} catch(Exception ex) {ex.printStackTrace();}
			try {rs.close();} catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();} catch(Exception ex) {ex.printStackTrace();}
		}
		return null;
	}
	
	public static List<Film> getAll(String naziv,String zanr, int najmanjaMinutaza,int najvecaMinutaza, String distributer,String zemljaPorijekla,int najranijaGodina,int posljednjaGodina) throws Exception {
		List<Film> filmovi = new ArrayList<>(); 
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null; 
		ResultSet rs = null ; 
		
		try {
			String query = "SELECT id,naziv, zanr,trajanje,distributer,zemljaPorijekla,godinaProizvodnje FROM filmovi WHERE" +
		"naziv like ? AND zanr like ?  AND trajanje LIKE ? AND trajanje LIKE ? AND distributer LIKE ? AND zemljaPorijekla LIKE ? AND godinaProizvodnje <= ? AND godinaProizvodnje >= ?"; 
			prep = conn.prepareStatement(query);
			int index = 1; 
			prep.setString(index ++, "%" + naziv + "%");
			prep.setString(index++, "%" + zanr.toString() + "%"); 
			prep.setInt(index++, najmanjaMinutaza);
			prep.setInt(index++, najvecaMinutaza);
			prep.setString(index++, "%" + distributer + "%");
			prep.setString(index++, "%" + zemljaPorijekla + "%");
			prep.setInt(index++, najranijaGodina);
			prep.setInt(index++, posljednjaGodina);
			System.out.println(prep);
			
			rs = prep.executeQuery(); 
			
			while (rs.next()) {
				index = 1; 
				int filmID = rs.getInt(index++);
				String nazivFilma = rs.getString(index++);
				Zanrovi zanrFilma = Zanrovi.valueOf(rs.getString(index++));
				String trajanjeFilma = rs.getString(index++); 
				String distributerFilma = rs.getString(index++); 
				String zemljaPorijeklaFilma = rs.getString(index++); 
				int godinaProizvodnjeFilma = rs.getInt(index++); 
					
				Film film = new Film(filmID,nazivFilma,zanrFilma,trajanjeFilma,distributerFilma,zemljaPorijeklaFilma,godinaProizvodnjeFilma);
				filmovi.add(film);	
				System.out.println(film);
			}
			
		}  finally {
			try {prep.close();} catch(Exception e){e.printStackTrace();}
			try {rs.close();} catch(Exception e) {e.printStackTrace();}
			try {conn.close();} catch(Exception e) {e.printStackTrace();}
		} 	
		return filmovi;
	}
	
	public static boolean add(Film film) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null ; 
		try {
			String query = "INSERT INTO filmovi (naziv,zanr,trajanje,distributer,zemljaPorijekla,godinaProizvodnje)"
					+ "VALUES (?,?,?,?,?,?,?)";
			prep = conn.prepareStatement(query);
			int index = 1; 
			
			prep.setString(index++, film.getNaziv());
			prep.setString(index++,film.getZanrovi().toString());
			prep.setString(index++, film.getTrajanje());
			prep.setString(index++, film.getDistributer());
			prep.setString(index++, film.getZemljaPorijekla());
			prep.setInt(index++, film.getGodinaProizvodnje());
			System.out.println(prep);
			
			return prep.executeUpdate() == 1;
		} 
		finally {
			try {prep.close();} catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();} catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	public static boolean update(Film film) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null; 
		try {
			String query = "UPDATE filmovi SET naziv = ?, zanr = ?, trajanje = ?,distributer = ?, zemljaPorijekla = ?, godinaProizvodnje=?" 
					+ "WHERE id = ?";
			
			prep = conn.prepareStatement(query);
			int index = 1; 
			prep.setString(index++, film.getNaziv());
			prep.setString(index++, film.getZanrovi().toString());
			prep.setString(index++, film.getTrajanje());
			prep.setString(index++, film.getDistributer());
			prep.setString(index++, film.getZemljaPorijekla());
			prep.setInt(index++, film.getGodinaProizvodnje());
			prep.setInt(index++, film.getId());
			System.out.println(prep);			
	
			return prep.executeUpdate() == 1;
		} 
		finally {
			try {prep.close();} catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();} catch(Exception ex) {ex.printStackTrace();}
		} 
	} 
	
	public static boolean delete (int id) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null; 
		try {
			String query = "DELETE FROM filmovi WHERE id = ?";
			prep = conn.prepareStatement(query);
			prep.setInt(1, id);
			System.out.println(prep); 
			return prep.executeUpdate()==1;
		} 
		finally {
			try {prep.close();} catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();} catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	}
	

