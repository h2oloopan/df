/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * TemplateHandler.java
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
public class TemplateHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult)
    {
        return previousResult;
    }

    @Override
    public boolean hasRecursion()
    {
        return true;
    }

    @Override
    public Set<String> getIgnoredAttributes()
    {
        return null;
    }

}
