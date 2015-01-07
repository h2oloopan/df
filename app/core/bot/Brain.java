/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Knowledge.java
 *
 */
package core.bot;

import java.util.HashMap;

import org.alicebot.ab.PreProcessor;

import core.bot.knowledge.*;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 7, 2015
 */
//A brain here is like a bot for program-ab
public class Brain
{
    private String name;
    private Properties properties;
    private PreProcessor preProcessor;
    private Graphmaster graph;
    private HashMap<String, AIMLSet> setMap;
    private HashMap<String, AIMLMap> mapMap;
    
    public Barin() {
        
    }
}
