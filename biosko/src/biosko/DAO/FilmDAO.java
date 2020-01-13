package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bioskop.model.Film;
import bioskop.model.Zanr;


public class FilmDAO {
	public static Film get(String id) throws SQLException {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT naziv,zanr,trajanje,distributer,zemljaPorijekla,godinaProizvodnje FROM filmovi WHERE id = ?";
			prep = conn.prepareStatement(query);
			prep.setString(1, id);
			System.out.println(prep);
			rs = prep.executeQuery();
			
			if(rs.next()) {
				int index = 1;
				String naziv = rs.getString(index++);
				Zanr zanr = Zanr.valueOf(rs.getString(index++));
				int trajanje = rs.getInt(index++);
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
	
	public static List<Film> getAll(String naziv, Zanr zanr, String najmanjaMinutaza,String najvecaMinutaza, String distributer, String zemljaPorijekla,int najranijaGodina,int posljednjaGodina ) throws SQLException {
		List <Film> filmovi = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		ResultSet rs = null;
		try {
			String query = "SELECT id, naziv,zanr,trajanje,distributer,zemljaPorijekla, godinaProizvodnje FROM filmovi WHERE " + "naziv LIKE ? AND zanr ? "
					+ "AND trajanje >= ? AND trajanje <= ? AND distributer LIKE ? AND zemljaPorijekla LIKE ? AND godinaProizvodnje >= ? AND godinaProizovodnje <= ?";
			
			prep = conn.prepareStatement(query);
			int index = 1; 
			prep.setString(index++, "%" + naziv + "%");
			prep.setString(index++, "%" + najmanjaMinutaza + "%");
			prep.setString(index++, "%" + najvecaMinutaza + "%");
			prep.setString(index++, "%" + distributer + "%");
			prep.setString(index++, "%" + zemljaPorijekla + "%");
			prep.setInt(index++, najranijaGodina);
			prep.setInt(index++ , posljednjaGodina);
			System.out.println(prep);
			
			rs = prep.executeQuery();
			
			while(rs.next()) {
				index = 1; 
				String filmID = rs.getString(index++);
				String filmNaziv = rs.getString(index++); 
				Zanr filmZanr = Zanr.valueOf(rs.getString(index++));
				String filmTrajanje = rs.getString(index++);
				String filmDistributer = rs.getString(index++);
				String filmZemljaPorijekla = rs.getString(index++);
				int filmGodinaProizvodnje = rs.getInt(index++);

				
			Film film = new Film(filmID, filmNaziv,filmZanr,filmTrajanje,filmDistributer,filmZemljaPorijekla,filmGodinaProizvodnje);
			filmovi.add(film);
			}
		} finally {
			try {prep.close();} catch(Exception e) {e.printStackTrace();}
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
			prep.setString(index++, film.getZanr().toString());
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
			prep.setString(index++, film.getZanr().toString());
			prep.setString(index++, film.getTrajanje());
			prep.setString(index++, film.getDistributer());
			prep.setString(index++, film.getZemljaPorijekla());
			prep.setInt(index++, film.getGodinaProizvodnje());
			prep.setString(index++, film.getId());
			System.out.println(prep);			
			return prep.executeUpdate() == 1;
		} 
		finally {
			try {prep.close();} catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();} catch(Exception ex) {ex.printStackTrace();}
		} 
	} 
	
	public static boolean delete (String id) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null; 
		try {
			String query = "DELETE FROM filmovi WHERE id = ?";
			prep = conn.prepareStatement(query);
			prep.setString(1, id);
			System.out.println(prep); 
			return prep.executeUpdate()==1;
		} 
		finally {
			try {prep.close();} catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();} catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	}
	

