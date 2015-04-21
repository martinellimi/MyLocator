import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DataBaseConnection {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.11.52.130/mylocator";

	// Database credentials
	static final String USER = "admin4tV81q1";
	static final String PASS = "ypR4kNRZ98tW";

	public Connection getConnection() {
		Connection conn = null;
		
		try {
			
			Class.forName(JDBC_DRIVER);
			
			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public void insertUser(String id, String name) {

		Connection conn = getConnection();

		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();

			String sql = "INSERT INTO USER (NAME, FACEID, LOGGED) VALUES ('"
					+ name + "'," + id + "," + Boolean.TRUE + ")";
			stmt.executeUpdate(sql);

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public UserModel selectUser(String idFace) {

		Connection conn = getConnection();

		Statement stmt = null;

		UserModel user = null;

		try {

			stmt = conn.createStatement();

			String sql = "SELECT ID, NAME, FACEID, LOGGED FROM USER";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				user = new UserModel();
				user.setId(rs.getInt("ID"));
				user.setName(rs.getString("NAME"));
				user.setIdFace(rs.getString("FACEID"));
				user.setLogged(rs.getBoolean("LOGGED"));
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<LocationModel> selectLocations(Integer idUser) {

		Connection conn = getConnection();

		Statement stmt = null;

		LocationModel location = null;
		
		List<LocationModel> locations = new ArrayList<LocationModel>();

		try {

			stmt = conn.createStatement();

			String sql = "SELECT ID, LATITUDE, LONGITUDE, IDUSER FROM LOCATION WHERE IDUSER = " + idUser;

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				location = new LocationModel();
				location.setIdUser(rs.getInt("IDUSER"));
				location.setLatitude(rs.getString("LATITUDE"));
				location.setLongitude(rs.getString("LONGITUDE"));
				location.setId(rs.getInt("ID"));
				locations.add(location);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	public void insertLocation(Integer idUser, String lat, String lon) {

		Connection conn = getConnection();

		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();

			String sql = "INSERT INTO LOCATION (LATITUDE, LONGITUDE, IDUSER) VALUES ('"
					+ lat + "'," + "'" + lon + "'," + idUser + ")";
			stmt.executeUpdate(sql);

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
