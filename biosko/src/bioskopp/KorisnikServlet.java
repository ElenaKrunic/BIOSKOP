package bioskopp;

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


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import biosko.DAO.ConnectionManager;
import biosko.DAO.KorisnikDAO;
import bioskop.model.Korisnik;


/**
 * Servlet implementation class KorisnikServlet
 */
public class KorisnikServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws IOException {
		PrintWriter out = response.getWriter(); 
		String action = request.getParameter("action"); 
		String ID = request.getParameter("korisnikID"); 
		
		if (action != null && request != null) {
			switch(action) {
			//ovde mi je registrationSubmit, ID submita iz HTML-a i onClick iz Registracija.js 
			case "registration" : 
				out.print(registerUser(request));
				break; 
			case "login" :
				out.print(loginUser(request));
				break; 
				
			case "getUserSessionInfo" : 
				out.print(podaciOulogovanomKorisniku(request));
				break;
			}
		}
	}
	
	private JSONObject podaciOulogovanomKorisniku(HttpServletRequest request) {
		// TODO Auto-tub
	return KorisnikDAO.getKorisnikInfo(request); 	
	}

	private JSONObject loginUser(HttpServletRequest request) {
		JSONObject response = KorisnikDAO.login(request); 
		return response;
	}

	//izlazi da bude JSON Object
	private JSONObject registerUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		JSONObject response = new JSONObject(); 
		response = KorisnikDAO.register(request); 
		return response;
	}
	
	private JSONObject loadAllUsers() {
		JSONObject response = new JSONObject(); 
		response = KorisnikDAO.loadAllUsers("", "", "", "");
		return response; 
	}
/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ulogovanKorisnikIme = (String) request.getSession().getAttribute("ulogovanKorisnikIme");
		if (ulogovanKorisnikIme == null) {
			request.getRequestDispatcher("./OdjavaServlet").forward(request, response);
			return;
		}
		try {
			Korisnik ulogovaniKorisnik = KorisnikDAO.get(ulogovanKorisnikIme);
			if (ulogovaniKorisnik == null) {
				request.getRequestDispatcher("./OdjavaServlet").forward(request, response);
				return;
			}
	
		String korisnickoIme = request.getParameter("korisnickoImeKorisnik"); 
		Korisnik korisnik = KorisnikDAO.get(korisnickoIme);
		
		
		Map<String,Object> data = new LinkedHashMap<String,Object>(); 
		data.put("korisnik", korisnik); 
		//provjera 
		System.out.println(data); 
		
		request.setAttribute("data", data);
		request.getRequestDispatcher("/.SuccessServlet").forward(request, response);
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

*/
}
