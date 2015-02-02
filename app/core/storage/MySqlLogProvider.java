/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * MySqlLogProvider.java
 *
 */
package core.storage;

import java.sql.*;
import java.util.HashMap;

import javax.sql.DataSource;

import core.context.Profile;
import play.db.DB;
import play.libs.Json;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 30, 2015
 */
public class MySqlLogProvider implements LogProvider
{
    private final DataSource ds;
    
    public MySqlLogProvider() {
        this.ds = DB.getDataSource();
    }
    

    @Override
    public void saveQuery(String bot, String topic, String inputOriginal, String inputParsed, String output, String uid) throws Exception
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ds.getConnection();
            String sql = "INSERT INTO query_logs (bot, topic, input_original, input_parsed, output, uid, timestamp) VALUES (?, ?, ?, ?, ?, ?, NOW());";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, bot);
            stmt.setString(2, topic);
            stmt.setString(3, inputOriginal);
            stmt.setString(4, inputParsed);
            stmt.setString(5, output);
            stmt.setString(6, uid);
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
