/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Permission.java
 *
 */
package platform.models;

import javax.persistence.*;

import play.data.validation.*;
import play.db.ebean.*;
import play.db.ebean.Model.*;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Feb 26, 2015
 */

@Entity
@Table(name="permissions")
public class Permission
{
    @Id
    public int id;
    
    @Constraints.Required
    public String bot;
    
    @Constraints.Required
    public int user;
    
    @Constraints.Required
    public boolean read;
    
    @Constraints.Required
    public boolean admin;
    
    public static Finder<Integer, Permission> find = new Finder<Integer, Permission>(Integer.class, Permission.class);
}
