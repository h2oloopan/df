/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SystemHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.MagicStrings;
import core.bot.ab.ParseState;
import core.bot.ab.Utilities;
import core.bot.ab.utils.IOUtils;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class SystemHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        HashSet<String> attributeNames = Utilities.stringSet("timeout");
        String evaluatedContents = ps.bot.handlers.getHandler("default").handle(node, ps, "", attributeNames);
        String result = IOUtils.system(evaluatedContents, MagicStrings.system_failed);
        return result;
    }

}
