/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * User.java
 *
 */
package platform.models;

import javax.persistence.*;

import play.data.validation.*;
import play.db.ebean.*;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Feb 26, 2015
 */

@Entity
@Table(name="users")
public class User extends Model
{
    @Id
    public int id;
    
    @Constraints.Required
    public String username;
    
    @Constraints.Required
    public String password;
    
    public String email;
    
    public String key;
    
    public static Finder<Integer, User> find = new Finder<Integer, User>(Integer.class, User.class);
    
}
