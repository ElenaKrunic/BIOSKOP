package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bioskop.model.TipProjekcije;

public class TipProjekcijeDAO {

	public static TipProjekcije getTipProjekcijeObjectById(int id) {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		TipProjekcije tipProjekcije = null;
		
		try {
			String query = "SELECT ID,Naziv FROM Tipovi_Projekcija WHERE ID = ?";
			prep = conn.prepareStatement(query); 
			prep.setString(1, String.valueOf(id));
			rs = prep.executeQuery(); 
			
			if(rs.next()) {
				int index = 1; 
				int idP = Integer.valueOf(rs.getString(index++)); 
				String naziv = rs.getString(index++); 
				tipProjekcije = new TipProjekcije(idP, naziv); 
			} else { 
				
			}
		} catch(Exception e) {
			System.out.println("Ne postoji tip projekcije sa datim ID-jem"); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return tipProjekcije;
	}
	/*
	
	public static TipProjekcije getTipProjekcijeObject(int idTipaProjekcije) throws SQLException {
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null;
		TipProjekcije tipProjekcije = null; 
		
		try {
			String query = "SELECT ID,Naziv FROM Tipovi_Projekcija WHERE ID = ?";
			
			prep = conn.prepareStatement(query); 
			prep.setString(1,String.valueOf(idTipaProjekcije)); 
			
			rs = prep.executeQuery(); 
			if(rs.next()) {
				int index = 1; 
				int ID = Integer.valueOf(rs.getString(index++)); 
				String Naziv = rs.getString(index++); 
				tipProjekcije = new TipProjekcije(ID,Naziv); 
			}
			else {
				System.out.println("Ne postoji tip projekcije"); 
			}
		} finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		return tipProjekcije; 
	}
	*/
}
