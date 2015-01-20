/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * BotHandler.java
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
public class BotHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result = MagicStrings.default_property;
        //HashSet<String> attributeNames = Utilities.stringSet("name");
        String propertyName = HandlingHelper.getAttributeOrTagValue(node, ps, "name", handlers);
        if (propertyName != null)
           result = ps.bot.properties.get(propertyName).trim();
        //System.out.println("BOT: "+m.getNodeValue()+"="+result);
        return result;
    }

}
