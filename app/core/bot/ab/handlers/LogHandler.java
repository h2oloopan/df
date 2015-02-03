/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * LogHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.bot.ab.ParseState;
import core.bot.ab.Utilities;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Feb 3, 2015
 */
public class LogHandler extends TagHandler
{
    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String type = HandlingHelper.getAttributeOrTagValue(node, ps, "type", handlers);
        String note = HandlingHelper.getAttributeOrTagValue(node, ps, "note", handlers);
        
        HashSet<String> attributeNames = Utilities.stringSet("type", "note");
        String log = "";
        
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            log += handlers.getDefaultHandler().handle(child, ps, "", attributeNames);
        }
        
        ps.bot.logProvider.saveGeneral(log.trim(), note, type);
        
        return "";
    }
}
