package biosko.DAO;

import java.sql.Connection;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class ConnectionManager {
	private static final String DATABASE_NAME = "biosko.db";

	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String ELENA_KRUNIC = "C:" + FILE_SEPARATOR + "SQLiteStudio-3.2.1" + FILE_SEPARATOR + DATABASE_NAME;
	@SuppressWarnings("unused")
	private static final String LINUX_PATH = "SQLite" + FILE_SEPARATOR + DATABASE_NAME;

	private static final String PATH = ELENA_KRUNIC;	

	private static DataSource dataSource;

	public static void open() {
		try {
			Properties dataSourceProperties = new Properties();
			dataSourceProperties.setProperty("driverClassName", "org.sqlite.JDBC");
			dataSourceProperties.setProperty("url", "jdbc:sqlite:" + PATH);
			
			dataSource = BasicDataSourceFactory.createDataSource(dataSourceProperties); // connection pool
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return dataSource.getConnection(); // slobodna konekcija se vadi iz pool-a na zahtev
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return null;
	}
}
