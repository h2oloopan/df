/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * LogProvider.java
 *
 */
package core.storage;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 30, 2015
 */
public interface LogProvider
{
    public void saveQuery(String inputOriginal, String inputParsed, String output, String uid) throws Exception;
}
