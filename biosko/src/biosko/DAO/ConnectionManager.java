package biosko.DAO;

import java.sql.Connection;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class ConnectionManager {
	private static final String DATABASE_NAME = "Bioskop.db";
	private static final String db_name = "Bioskop.db";
	
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");

	private static final String WINDOWS_PATH = "C:" + FILE_SEPARATOR + "Users" + FILE_SEPARATOR + "WIN7" + FILE_SEPARATOR + "git" + FILE_SEPARATOR + "BIOSKOP" + FILE_SEPARATOR +
			"biosko" + FILE_SEPARATOR + "WebContent" + FILE_SEPARATOR + "sql" + FILE_SEPARATOR + DATABASE_NAME ; 
	//@SuppressWarnings("unused")
	//PUTANJA
	//C:\Users\WIN7\Desktop
	//treba fs izmedju sqlite i db fajla 
	//private static final String LINUX_PATH = "C:" + FILE_SEPARATOR + "Users" + FILE_SEPARATOR + "WIN7" + FILE_SEPARATOR + "Desktop" + FILE_SEPARATOR + Baza2;

	//private static final String PATH = WINDOWS_PATH;	
	
//	/C:\Users\WIN7\Downloads\BIOSKOP-master\biosko\WebContent\sql
	
	private static final String TEST_PATH = "C:" + FILE_SEPARATOR + "Users" + FILE_SEPARATOR + "WIN7" + FILE_SEPARATOR + 
			"Downloads" + FILE_SEPARATOR + "BIOSKOP-master" + FILE_SEPARATOR + "biosko" + FILE_SEPARATOR + "WebContent" + FILE_SEPARATOR + 
			"sql" + FILE_SEPARATOR + db_name;
	
	private static final String PATH = TEST_PATH;
	
	private static DataSource dataSource;

	public static void open() {
		try {
			Properties dataSourceProperties = new Properties();
			dataSourceProperties.setProperty("driverClassName", "org.sqlite.JDBC");
			dataSourceProperties.setProperty("url", "jdbc:sqlite:" + PATH);
			
			dataSource = BasicDataSourceFactory.createDataSource(dataSourceProperties); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}
