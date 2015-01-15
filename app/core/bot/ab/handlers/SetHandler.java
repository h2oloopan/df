/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SetHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;
import core.bot.ab.Utilities;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 15, 2015
 */
public class SetHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        HashSet<String> attributeNames = Utilities.stringSet("name", "var");
        String predicateName = HandlingHelper.getAttributeOrTagValue(node, ps, "name");
        String varName = HandlingHelper.getAttributeOrTagValue(node, ps, "var");
        String result = ps.bot.handlers.getHandler("default").handle(node, ps, "", attributeNames).trim();
        result = result.replaceAll("(\r\n|\n\r|\r|\n)", " ");
        if (predicateName != null) {
            ps.bot.predicates.put(predicateName, result);
        }
        if (varName != null) {
            ps.vars.put(varName, result);
        }
        if (ps.bot.pronounSet.contains(predicateName)) {
            result = predicateName;
        }
        return result;
        
    }

}
