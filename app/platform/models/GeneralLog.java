/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * GeneralLog.java
 *
 */
package platform.models;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.*;
import play.db.ebean.*;
import play.db.ebean.Model.*;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Mar 12, 2015
 */
@Entity
@Table(name="general_logs")
public class GeneralLog extends Model
{
    @Id
    public int id;
    
    public Date timestamp;
    
    public String log;
    
    public String note;
    
    public String type;
    
    public static Finder<Integer, GeneralLog> find = new Finder<Integer, GeneralLog>(Integer.class, GeneralLog.class);
    
    public GeneralLog() {
        
    }
    
    public GeneralLog(Date timestamp, String log, String note, String type) {
        this.timestamp = timestamp;
        this.log = log;
        this.note = note;
        this.type = type;
    }
}
