/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * CQAHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.bot.ab.ParseState;
import core.bot.ab.Utilities;
import core.bot.ab.utils.NetworkUtils;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Feb 4, 2015
 */
public class CQAHandler extends TagHandler
{
    private final long TIMEOUT = 5000;
    
    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String def = HandlingHelper.getAttributeOrTagValue(node, ps, "default", handlers);
        HashSet<String> attributeNames = Utilities.stringSet("default");
        String query = "";
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            query += handlers.getDefaultHandler().handle(child, ps, "", attributeNames);
        }
        try {
            return NetworkUtils.cqa(query).get(TIMEOUT);
        } catch (Exception ex) {
            return def;
        }
    }
}
