/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * RequestHandling.java
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
public class RequestHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        int index=HandlingHelper.getIndexValue(node, ps);
        //return ps.chatSession.requestHistory.getString(index).trim();
        return ps.context.getLastQuery(index).trim();
    }

}
