package core.storage;

import java.sql.*;
import java.util.HashMap;

import javax.sql.DataSource;

import core.context.Profile;
import play.Logger;
import play.libs.Json;
import play.db.*;

public class MySqlProfileProvider implements ProfileProvider {
	
	private final DataSource ds;
	
	public MySqlProfileProvider() {
		this.ds = DB.getDataSource();
	}

	@Override
	public Profile getProfile(String uid) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ds.getConnection();
			String sql = "SELECT * FROM profiles WHERE uid = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				String name = result.getString("name");
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = Json.fromJson(Json.parse(result.getString("map")), HashMap.class);
				Date createdDate = result.getDate("created_date");
				return new Profile(uid, name, map, createdDate);
			} else {
				//create a new profile
			    return new Profile(uid);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//try to close open statement and connection
			try {
				if (stmt != null) stmt.close();
			} catch (Exception ex) {
				//do nothing
				throw ex;
			}
			try {
				if (conn != null) conn.close();
			} catch (Exception ex) {
				//do nothing
				throw ex;
			}
		}
	}

	@Override
	public void saveProfile(String uid, Profile profile) throws Exception {
	    if (profile == null) {
	        throw new NullPointerException("There is no profile to save");
	    }
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ds.getConnection();
			String sql = "INSERT INTO profiles (uid, name, map, created_date) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, map = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			if (profile.name != null) {
			    stmt.setString(2, profile.name);
			    stmt.setString(5, profile.name);
			} else {
			    stmt.setNull(2, Types.NULL);
			    stmt.setNull(5, Types.NULL);
			}
			stmt.setDate(4, new java.sql.Date(profile.createdDate.getTime()));
			stmt.setString(3, Json.stringify(Json.toJson(profile.map)));
            stmt.setString(6, Json.stringify(Json.toJson(profile.map)));
            stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			//try to close open statement and connection
			try {
				if (stmt != null) stmt.close();
			} catch (Exception ex) {
				//do nothing
				throw ex;
			}
			try {
				if (conn != null) conn.close();
			} catch (Exception ex) {
				//do nothing
				throw ex;
			}
		}
	}

}