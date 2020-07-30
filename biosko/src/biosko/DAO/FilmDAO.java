package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import bioskop.model.Film;
import bioskop.model.Zanr;
import bioskop.model.Zanrovi;


public class FilmDAO {
	
	
	//SVI FILMOVI
	public static ArrayList<JSONObject> getMovies(String nazivF,int trajanjeF,String zanroviF,String opisF,String glumciF,String reziserF,String godinaF,String distributerF,String zemljaF) throws SQLException {
		
		ArrayList<JSONObject> filmovi = new ArrayList<JSONObject>(); 
		
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		try {
			String query = "SELECT ID, Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis,Status FROM Filmovi"
					+ " WHERE Naziv LIKE ? AND Reziser LIKE ? AND Glumci LIKE ? AND Zanrovi LIKE ? AND Trajanje>? AND Distributer LIKE ? AND Zemlja_Porekla LIKE ? AND Godina_Proizvodnje LIKE ? AND Opis LIKE ?";
			
			prep = conn.prepareStatement(query); 
			prep.setString(1,"%" + nazivF + "%");
			prep.setString(2,"%" + reziserF + "%");
			prep.setString(3,"%" + glumciF + "%");
			prep.setString(4,"%" + zanroviF + "%");
			prep.setInt(5,trajanjeF);
			prep.setString(6,"%" + distributerF + "%");
			prep.setString(7,"%" + zemljaF + "%");
			prep.setString(8,"%" + godinaF + "%");
			prep.setString(9,"%" + opisF + "%");
			
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				int ID = Integer.valueOf(rs.getString(index++)); 
				String Naziv = rs.getString(index++); 
				String Reziser = rs.getString(index++); 
				String Glumci = rs.getString(index++); 
				String Zanrovi = rs.getString(index++); 
				int Trajanje = Integer.valueOf(rs.getString(index++)); 
				String Distributer = rs.getString(index++); 
				String ZemljaPorijekla = rs.getString(index++); 
				int godinaP = Integer.valueOf(rs.getString(index++)); 
				String Opis = rs.getString(index++); 
				String status = rs.getString(index++); 

				ArrayList<Zanr> zanrovi = new ArrayList<Zanr>(); 
				String[] zanrovi999 = Zanrovi.split(";");
				if(zanrovi999.length>0) {
					for (String z : zanrovi999) {
						try {
							zanrovi.add(Zanr.valueOf(z)); 
						} catch(Exception e) {
							System.out.println("Zanr zajebava"); 
							e.printStackTrace();
						}
					}
				} else {
					zanrovi.add(Zanr.valueOf("Nista")); 
				}
				
				Film film = new Film(ID, Naziv, Reziser, Glumci, zanrovi, Trajanje, Distributer, ZemljaPorijekla, godinaP, Opis);
				JSONObject jsonFilm = new JSONObject(); 
				jsonFilm.put("ID", film.getId()); 
				jsonFilm.put("Naziv", film.getNaziv()); 
				jsonFilm.put("Reziser", film.getReziser()); 
				String[] gl = film.getGlumci().split(";"); 
				ArrayList<String> glumci = new ArrayList<String>(); 
				for(String s : gl) {
					glumci.add(s);
				}
				jsonFilm.put("Glumci", glumci);
				ArrayList<String> zanrovi2 = new ArrayList<String>(); 
				for(Zanr zr : film.getZanr()) {
					zanrovi2.add(zr.toString()); 
				}
				jsonFilm.put("Zanrovi", zanrovi2); 
				jsonFilm.put("Trajanje", film.getTrajanje()); 
				jsonFilm.put("Distributer", film.getDistributer()); 
				jsonFilm.put("Zemlja_Porekla", film.getZemljaPorijekla()); 
				jsonFilm.put("Godina_Proizvodnje", film.getGodinaProizvodnje()); 
				jsonFilm.put("Opis",film.getOpis()); 
				
				if(status.equalsIgnoreCase("active")) {
					filmovi.add(jsonFilm);
				}
			}
			return filmovi; 

		} finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
			// ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
	}
	
	//PO ID-u
	public static JSONObject getById(String id) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement prep = null;
		ResultSet rs = null;
		try {
			String query = "SELECT ID, Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis,Status FROM Filmovi WHERE id = ?";

			prep = conn.prepareStatement(query);
			prep.setString(1,id); 

			rs = prep.executeQuery();
			if (rs.next()) {
				int index = 1;
				int ID = Integer.valueOf(rs.getString(index++));
				String Naziv = rs.getString(index++);
				String Reziser = rs.getString(index++);
				String Glumci = rs.getString(index++);
				String Zanrovi = rs.getString(index++);
				int Trajanje = Integer.valueOf(rs.getString(index++));
				String Distributer = rs.getString(index++);
				String Zemlja_Porekla = rs.getString(index++);
				int Godina_Proizvodnje = Integer.valueOf(rs.getString(index++));
				String Opis = rs.getString(index++);
				String status = rs.getString(index++);
				
				//Sredjivanje za pravljenje objekta
				ArrayList<Zanr> Zanrovi_n = new ArrayList<Zanr>();
				String[] Zanrs = Zanrovi.split(";");
				for (String znr : Zanrs) {
					try{
						Zanrovi_n.add(Zanr.valueOf(znr));
					}
					catch(Exception e){
						System.out.println("Puklo kod unosa zanra - "+e);
					}
				}
				Film film = new Film(ID, Naziv, Reziser, Glumci, Zanrovi_n, Trajanje, Distributer, Zemlja_Porekla, Godina_Proizvodnje, Opis);
				
				JSONObject jsonFilm = new JSONObject(); 
				jsonFilm.put("ID", film.getId()); 
				jsonFilm.put("Naziv", film.getNaziv()); 
				jsonFilm.put("Reziser", film.getReziser()); 
				String[] gl = film.getGlumci().split(";"); 
				ArrayList<String> glumci = new ArrayList<String>(); 
				for(String s : gl) {
					glumci.add(s);
				}
				jsonFilm.put("Glumci", glumci);
				ArrayList<String> zanrovi2 = new ArrayList<String>(); 
				for(Zanr zr : film.getZanr()) {
					zanrovi2.add(zr.toString()); 
				}
				jsonFilm.put("Zanrovi", zanrovi2); 
				jsonFilm.put("Trajanje", film.getTrajanje()); 
				jsonFilm.put("Distributer", film.getDistributer()); 
				jsonFilm.put("Zemlja_Porekla", film.getZemljaPorijekla()); 
				jsonFilm.put("Godina_Proizvodnje", film.getGodinaProizvodnje()); 
				jsonFilm.put("Opis",film.getOpis()); 
				
				if(status.equalsIgnoreCase("active")) {
					return jsonFilm;
				}
				else { 
					System.out.println("Ne vraca 1 film"); 
				}
			}
		} finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		
		return null; 
}
	
	public static Film getFilmObjectById(int id1) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT ID, Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis,Status FROM Filmovi WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, String.valueOf(id1));

			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 1;
				int ID = Integer.valueOf(rset.getString(index++));
				String naziv = rset.getString(index++);
				String reziser = rset.getString(index++);
				String glumci = rset.getString(index++);
				String zanrovi = rset.getString(index++);
				int trajanje = Integer.valueOf(rset.getString(index++));
				String distributer = rset.getString(index++);
				String zemljaPorijekla = rset.getString(index++);
				int godinaProizvodnje = Integer.valueOf(rset.getString(index++));
				String opis = rset.getString(index++);
				String status = rset.getString(index++);
			
				ArrayList<Zanr> nizZanrovi = new ArrayList<Zanr>();
				String[] stringZanrovi = zanrovi.split(";");
				for (String zanr : stringZanrovi) {
					nizZanrovi.add(Zanr.valueOf(zanr));
				}
				Film film = new Film(ID, naziv, reziser, glumci, nizZanrovi, trajanje, distributer, zemljaPorijekla, godinaProizvodnje, opis);
				return film;
			}
			else {
				System.out.println("Podaci nisu pronadjeni");
			}

		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		
		return null;
	}

	
	public static Film getNaziv(String naziv) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT ID, Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis,Status FROM Filmovi WHERE naziv = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,naziv);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 1;
				int ID = Integer.valueOf(rset.getString(index++));
				//String naziv = rset.getString(index++);
				String reziser = rset.getString(index++);
				String glumci = rset.getString(index++);
				String zanrovi = rset.getString(index++);
				int trajanje = Integer.valueOf(rset.getString(index++));
				String distributer = rset.getString(index++);
				String zemljaPorijekla = rset.getString(index++);
				int godinaProizvodnje = Integer.valueOf(rset.getString(index++));
				String opis = rset.getString(index++);
				String status = rset.getString(index++);
			
				ArrayList<Zanr> nizZanrovi = new ArrayList<Zanr>();
				String[] stringZanrovi = zanrovi.split(";");
				for (String zanr : stringZanrovi) {
					nizZanrovi.add(Zanr.valueOf(zanr));
				}
				Film film = new Film(ID, naziv, reziser, glumci, nizZanrovi, trajanje, distributer, zemljaPorijekla, godinaProizvodnje, opis);
				return film;
			}
			else {
				System.out.println("Podaci nisu pronadjeni");
			}

		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		
		return null;
	}

	public static ArrayList<String> getGenres() {
		ArrayList<String> zanrovi = new ArrayList<String>(); 
		
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		try {
			String query = "SELECT Zanr FROM Zanrovi WHERE 1"; 
			
			prep = conn.prepareStatement(query);
			rs = prep.executeQuery(); 
			
			while(rs.next()) {
				int index = 1; 
				String zanr = rs.getString(index++); 
				zanrovi.add(zanr); 
			}
		}
		
		catch(Exception e) {
			System.out.println("Zanr nije pronadjen"); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
		return zanrovi;
	}
	
	/*	
	/*
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
	*/
}
	

