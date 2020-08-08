package biosko.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import bioskop.model.Film;
import bioskop.model.Zanr;


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
					zanrovi.add(Zanr.valueOf("NeunesenZanr")); 
				} 
				
				Film film = new Film(ID, Naziv, Reziser, Glumci, zanrovi, Trajanje, Distributer, ZemljaPorijekla, godinaP, Opis);
				System.out.println("Obican film iz FilmDAO je " + film); 
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
				
				System.out.println("Json film iz FilmDAO je " + jsonFilm); 
				
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
			prep.setString(1, id);

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
				
				ArrayList<Zanr> Zanrovi_n = new ArrayList<Zanr>();
				String[] Zanrs = Zanrovi.split(";");
				for (String znr : Zanrs) {
					try{
						Zanrovi_n.add(Zanr.valueOf(znr));
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				Film film = new Film(ID, Naziv, Reziser, Glumci, Zanrovi_n, Trajanje, Distributer, Zemlja_Porekla, Godina_Proizvodnje, Opis);
				JSONObject obj = new JSONObject();
				obj.put("ID",film.getId());
				obj.put("Naziv",film.getNaziv());
				obj.put("Reziser",film.getReziser());
				String[] glumci1 = film.getGlumci().split(";");
				ArrayList<String> glumci = new ArrayList<String>();
				for (String string : glumci1) {
					glumci.add(string);
				}
				obj.put("Glumci",glumci);
				ArrayList<String> zanrovi = new ArrayList<String>();
				for (Zanr z : film.getZanr()) {
					zanrovi.add(z.toString());
				}
				obj.put("Zanrovi",zanrovi);
				obj.put("Trajanje",film.getTrajanje());
				obj.put("Distributer",film.getDistributer());
				obj.put("Zemlja_Porekla",film.getZemljaPorijekla());
				obj.put("Godina_Proizvodnje",film.getGodinaProizvodnje());
				obj.put("Opis",film.getOpis());
				obj.put("status",status);
				if(status.equalsIgnoreCase("active")) {
					return obj;
				}
				return null;
			}
			else {
				System.out.println("nista nije vraceno");
			}

		} finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rs.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
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
				String Naziv = rset.getString(index++);
				String Reziser = rset.getString(index++);
				String Glumci = rset.getString(index++);
				String Zanrovi = rset.getString(index++);
				int Trajanje = Integer.valueOf(rset.getString(index++));
				String Distributer = rset.getString(index++);
				String Zemlja_Porekla = rset.getString(index++);
				int Godina_Proizvodnje = Integer.valueOf(rset.getString(index++));
				String Opis = rset.getString(index++);
				String status = rset.getString(index++);
			
				ArrayList<Zanr> nizZanrovi = new ArrayList<Zanr>();
				String[] stringZanrovi = Zanrovi.split(";");
				for (String zanr : stringZanrovi) {
					nizZanrovi.add(Zanr.valueOf(zanr));
				}
				Film film = new Film(ID, Naziv, Reziser, Glumci, nizZanrovi, Trajanje, Distributer, Zemlja_Porekla, Godina_Proizvodnje, Opis);
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
	
	

	public static JSONObject editMovie(HttpServletRequest request) {
		JSONObject response = new JSONObject(); 
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		String id = request.getParameter("id"); 
		String naziv = request.getParameter("naziv"); 
		String reziser = request.getParameter("reziser"); 
		int trajanje = 34343; 
		try {
			trajanje = Integer.valueOf(request.getParameter("trajanje")); 
		} catch(Exception e) {
			System.out.println("Greska kod trajanja "); 
		}
		String zanr = request.getParameter("zanr"); 
		String glumci = request.getParameter("glumci"); 
		String distributer = request.getParameter("distributer"); 
		String zemlja = request.getParameter("zemlja"); 
		String godina = request.getParameter("godina"); 
		String opis = request.getParameter("opis"); 
		
		try {
			String query = "UPDATE Filmovi SET Naziv=?,Reziser=?,Glumci=?,Zanrovi=?,Trajanje=?,Distributer=?,Zemlja_Porekla=?,Godina_Proizvodnje=?,Opis=? WHERE ID = ?";
			
			prep = conn.prepareStatement(query); 
			
			prep.setString(1, naziv);
			prep.setString(2, reziser);
			prep.setString(3, glumci);
			prep.setString(4, zanr);
			prep.setString(5, String.valueOf(trajanje));
			prep.setString(6, distributer);
			prep.setString(7, zemlja);
			prep.setString(8, godina);
			prep.setString(9, opis);
			prep.setString(10, id);
			
			prep.executeUpdate();
			
			System.out.println("Izvrsena executeUpdate u editu"); 
		
	} catch(Exception e) { 
		e.printStackTrace();
	}
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool					}
	}
		
		return response;
	}

	public static boolean addMovie(HttpServletRequest request) {
		JSONObject response = new JSONObject(); 
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		ResultSet rs = null; 
		
		String naziv = request.getParameter("nazivFilma"); 
		String reziser = request.getParameter("reziserFilma"); 
		String glumci = request.getParameter("glumciFilma");
		//int trajanje = Integer.valueOf(request.getParameter("trajanjeFilma"));
		int trajanje = 34343; 
		try {
			trajanje = Integer.valueOf(request.getParameter("trajanjeFilma")); 
		} catch(Exception e) {
			System.out.println("Greska kod trajanja "); 
		}
		String zanrovi = request.getParameter("zanrFilma");
		String distributer = request.getParameter("distributerFilma"); 
		String zemlja = request.getParameter("zemljaPorijeklaFilma"); 
		int godina = Integer.valueOf(request.getParameter("godinaProizvodnjeFilma")); 
		String opis = request.getParameter("opisFilma"); 
		
		try {
			String query = "INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES (?,?,?,?,?,?,?,?,?)";

			prep = conn.prepareStatement(query); 
			
			prep.setString(1, naziv);
			prep.setString(2, reziser);
			prep.setString(3, glumci);
			prep.setString(4, zanrovi);
			prep.setInt(5, trajanje);
			prep.setString(6, distributer);
			prep.setString(7, zemlja);
			prep.setInt(8, godina);
			prep.setString(9, opis);	
			
			prep.executeUpdate();
			System.out.println("Izvrsan executeUpdate u add"); 
			
			} catch(Exception e) {
			System.out.println("Nije moguce dodati film "); 
		}
		
		finally {
			try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		return false;
	}


public static boolean deleteMovie(String id) {
	Connection conn = ConnectionManager.getConnection();
	PreparedStatement prep = null;
	try {
		String query = "UPDATE Filmovi SET Status='Deleted' WHERE ID = ?";

		prep = conn.prepareStatement(query);
		prep.setString(1, id);
		
		prep.executeUpdate(); 

	} 
	catch(Exception e) {
		System.out.println("Nije se obrisao film"); 
	}
	finally {
		try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
		try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		
	}
	return false;
	
}
}
	
	/*

	public static boolean deleteMovie(String filmId) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement prep = null; 
		
		try {
			String query = "UPDATE Filmovi SET Status='Deleted' WHERE ID = ?";
			
			prep  = conn.prepareStatement(query); 
			prep.setString(1, filmId);
			prep.executeUpdate();
			
			System.out.println("Izvrseno brisanje filma"); 
			
		} catch(Exception e) {
			System.out.println("Nije se obrisao"); 
		}
		
		 finally {
			 try {prep.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}  
		 }
		return false;
	}
}*/