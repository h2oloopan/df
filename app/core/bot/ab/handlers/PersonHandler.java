/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * PersonHandler.java
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
public class PersonHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result;
        if (node.hasChildNodes()) {
            result = handlers.getDefaultHandler().handle(node, ps, "", null);
        }
        else {
            result = ps.starBindings.inputStars.star(0);   // for <person/>
        }
        result = " "+result+" ";
        result = ps.bot.preProcessor.person(result);
        return result.trim();
    }

}
