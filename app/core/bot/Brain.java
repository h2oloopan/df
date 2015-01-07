/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Knowledge.java
 *
 */
package core.bot;

import java.util.HashMap;


import core.bot.knowledge.*;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 7, 2015
 */
//A brain here is like a bot for program-ab
public class Brain
{
    public String name;
    public Properties properties;
    public PreProcessor preProcessor;
    public Graphmaster graph;
    public HashMap<String, AIMLSet> setMap;
    public HashMap<String, AIMLMap> mapMap;
    
    public Brain(String name, String path) {
        this.name = name;
        //load everything
        this.graph = new Graphmaster(this);
    }
}
