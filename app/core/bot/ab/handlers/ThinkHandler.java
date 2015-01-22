/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ThinkHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class ThinkHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        HandlingHelper.handleChilderen(node, ps, previousResult, ignoreAttributes, handlers);
        return "";
    }

}
