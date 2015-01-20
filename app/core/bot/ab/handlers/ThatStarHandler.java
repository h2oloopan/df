/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ThatStarHandler.java
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
public class ThatStarHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        int index = HandlingHelper.getIndexValue(node, ps, handlers);
        if (ps.starBindings.thatStars.star(index)==null) return "";
        else return ps.starBindings.thatStars.star(index).trim();
    }

}
