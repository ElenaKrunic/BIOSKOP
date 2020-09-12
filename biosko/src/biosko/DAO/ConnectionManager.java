package biosko.DAO;

import java.sql.Connection;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class ConnectionManager {
	private static final String DATABASE_NAME = "Bioskop.db";
	
	//C:\Users\WIN7\git\BIOSKOP\biosko\WebContent\sql

	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	//private static final String ELENA_KRUNIC = "C:" + FILE_SEPARATOR + "SQLiteStudio-3.2.1" + FILE_SEPARATOR + DATABASE_NAME;
	//C:\Users\WIN7\git\BIOSKOP\biosko\WebContent\sql
	private static final String WINDOWS_PATH = "C:" + FILE_SEPARATOR + "Users" + FILE_SEPARATOR + "WIN7" + FILE_SEPARATOR + "git" + FILE_SEPARATOR + "BIOSKOP" + FILE_SEPARATOR +
			"biosko" + FILE_SEPARATOR + "WebContent" + FILE_SEPARATOR + "sql" + FILE_SEPARATOR + DATABASE_NAME ; 
	//@SuppressWarnings("unused")
	
	private static final String LINUX_PATH = "SQLite" + FILE_SEPARATOR + DATABASE_NAME;

	private static final String PATH = WINDOWS_PATH;	

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
