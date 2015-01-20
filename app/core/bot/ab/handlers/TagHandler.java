/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * TagHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 9, 2015
 */
public abstract class TagHandler
{
    protected TagHandlerCollection handlers;
    
    public TagHandler initialize(TagHandlerCollection handlers) {
        this.handlers = handlers;
        return this;
    }
    
    public String handle(Node node, ParseState ps) throws Exception {
        return this.handle(node, ps, "", null);
    }
    public abstract String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception;
}
