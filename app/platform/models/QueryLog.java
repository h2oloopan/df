/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * QueryLog.java
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
@Table(name="query_logs")
public class QueryLog extends Model
{
    @Id
    public int id;
    
    @Column(name="input_original")
    public String inputOriginal;
    
    @Column(name="input_parsed")
    public String inputParsed;
    
    public String output;
    
    public String uid;
    
    public Date timestamp;
    
    public String bot;
    
    public String topic;
    
    public static Finder<Integer, QueryLog> find = new Finder<Integer, QueryLog>(Integer.class, QueryLog.class);
    
    public QueryLog() {
        
    }
    
    public QueryLog(String inputOriginal, String inputParsed, String output, String uid, Date timestamp, String bot, String topic) {
        this.inputOriginal = inputOriginal;
        this.inputParsed = inputParsed;
        this.output = output;
        this.uid = uid;
        this.timestamp = timestamp;
        this.bot = bot;
        this.topic = topic;
    }
}
