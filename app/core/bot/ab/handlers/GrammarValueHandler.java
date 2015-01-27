/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * GrammarValueHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 27, 2015
 */
public class GrammarValueHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        
        String term = HandlingHelper.getAttributeOrTagValue(node, ps, "term", handlers);
        String key = HandlingHelper.getAttributeOrTagValue(node, ps, "key", handlers);
        
        ps.matcher
        
        return null;
    }

}
