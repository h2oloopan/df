/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SentenceHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class SentenceHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result = handlers.getDefaultHandler().handle(node, ps, "", null);
        if (result.length() > 1) return result.substring(0, 1).toUpperCase()+result.substring(1, result.length());
        else return "";
    }

}
