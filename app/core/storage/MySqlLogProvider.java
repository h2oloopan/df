/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * MySqlLogProvider.java
 *
 */
package core.storage;

//import java.sql.*;
import java.util.Date;
import java.util.HashMap;

import javax.sql.DataSource;

import core.context.Profile;
import platform.models.GeneralLog;
import platform.models.QueryLog;
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
        QueryLog qLog = new QueryLog(inputOriginal, inputParsed, output, uid, new Date(), bot, topic);
        qLog.save();
    }


    @Override
    public void saveGeneral(String log, String note, String type) throws Exception
    {
        GeneralLog gLog = new GeneralLog(new Date(), log, note, type);
        gLog.save();
    }

}
