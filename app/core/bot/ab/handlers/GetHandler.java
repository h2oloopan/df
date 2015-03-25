/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * GetHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.MagicStrings;
import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 15, 2015
 */
public class GetHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result = MagicStrings.default_get;
        String predicateName = HandlingHelper.getAttributeOrTagValue(node, ps, "name", handlers);
        String varName = HandlingHelper.getAttributeOrTagValue(node, ps, "var", handlers);
        String tupleName = HandlingHelper.getAttributeOrTagValue(node, ps, "tuple", handlers);
        if (predicateName != null)
           result = ps.context.retrievePredicate(predicateName).trim();
        else if (varName != null && tupleName != null) {
           result = HandlingHelper.tupleGet(tupleName, varName);
        }
        else if (varName != null) {
           result = ps.vars.get(varName).trim();
        }
        
        return result;
    }
}
