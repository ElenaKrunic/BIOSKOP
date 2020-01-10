package bioskopp;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosko.DAO.KorisnikDAO;
import bioskop.model.Korisnik;


/**
 * Servlet implementation class KorisnikServlet
 */
public class KorisnikServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
	
			Map<String, Object> data = new LinkedHashMap<>();
	
			String action = request.getParameter("action");
			switch (action) {
				case "ulogaKorisnika": {
					data.put("ulogaKorisnika", ulogovaniKorisnik.getUloga());
					break;
				}
			}
	
			request.setAttribute("data", data);
			request.getRequestDispatcher("./UspjesnoServlet").forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
