/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Person2Handler.java
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
public class Person2Handler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result;
        if (node.hasChildNodes()) {
            result = ps.bot.handlers.getHandler("default").handle(node, ps, "", null);
        } else {
            result = ps.starBindings.inputStars.star(0);   // for <person2/>
        }
        result = " "+result+" ";
        result = ps.bot.preProcessor.person2(result);
        return result.trim();
    }

}
