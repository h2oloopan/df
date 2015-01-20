/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Triple.java
 *
 */
package core.bot.ab;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 20, 2015
 */
public class Triple
{
    public String id;
    public String subject;
    public String predicate;
    public String object;
    
    public Triple() {
        //empty constructor
    }
    
    public Triple(String id, String s, String p, String o) {
        if (id != null && s != null && p != null && o != null)  {
            this.id = id;
            this.subject = s;
            this.predicate = p;
            this.object = o;
        }
    }
}
