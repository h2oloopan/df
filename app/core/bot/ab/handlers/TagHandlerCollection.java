/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * TagHandlerCollection.java
 *
 */
package core.bot.ab.handlers;

import java.util.HashMap;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 9, 2015
 */
public class TagHandlerCollection extends HashMap<String, TagHandler>
{
    public TagHandlerCollection() {
        super();
        this.put("default", new DefaultHandler());
        this.put("random", new RandomHandler());
        this.put("condition", new ConditionHandler());
        this.put("set", new SetHandler());
        this.put("get", new GetHandler());
        this.put("map", new MapHandler());
        this.put("bot", new BotHandler());
    }
    
    public TagHandler getHandler(String tag) {
        TagHandler handler = this.get(tag.trim().toLowerCase());
        if (handler == null) {
            return this.get("default");
        } else {
            return handler;
        }
    }
}
