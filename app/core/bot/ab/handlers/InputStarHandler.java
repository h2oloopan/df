/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * StarHandler.java
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
public class InputStarHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result= "" ;
        int index= HandlingHelper.getIndexValue(node, ps);
        if (ps.starBindings.inputStars.star(index)==null) result = "";
        else result = ps.starBindings.inputStars.star(index).trim();
        return result;
    }

}
