/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ExplodeHandler.java
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
public class ExplodeHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result = HandlingHelper.handleChilderen(node, ps, previousResult, ignoreAttributes, handlers);
        return explode(result);
    }
    
    private String explode(String input) {
        String result = "";
        for (int i = 0; i < input.length(); i++) result += " "+input.charAt(i);
        while (result.contains("  ")) result = result.replace("  "," ");
        return result.trim();
    }
    

}
