package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import bioskop.model.Projekcija;

public class ProjekcijeDAO {

	public static ArrayList<Projekcija> ucitajZaDanasnjiDatum(HttpServletRequest request, String danasnjiDatum) {
		ArrayList<Projekcija> lista = new ArrayList<Projekcija>(); 
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		try {
			String query = "SELECT ID FROM Projekcije  WHERE Status='Active' AND Termin BETWEEN ? AND ? ORDER BY ID_Filma ASC,Termin ASC ;";
			prep = conn.prepareStatement(query); 
			
			prep.setString(1,danasnjiDatum);
			prep.setString(2, danasnjiDatum+" 23:59:59");
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String id = rs.getString(index++); 
				Projekcija projekcija = getProjekcijaObjectById(Integer.valueOf(id)); 
				lista.add(projekcija);
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
	

	private static Projekcija getProjekcijaObjectById(int id) {
		Connection connection = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		Projekcija projekcija = null; 
		
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
				
			}
		} catch(Exception e) {
			System.out.println("Ne postoji projekcija");
		}
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {connection.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return projekcija;
	}


}
