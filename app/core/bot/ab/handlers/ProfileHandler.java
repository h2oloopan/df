/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ProfileHandler.java
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
 *@date Mar 4, 2015
 */
public class ProfileHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        // TODO Auto-generated method stub
        HashSet<String> attributeNames = Utilities.stringSet("action", "key", "value");
        String action = HandlingHelper.getAttributeOrTagValue(node, ps, "action", handlers);
        String key = HandlingHelper.getAttributeOrTagValue(node, ps, "key", handlers);
        if (key == null || key.equals("")) {
            throw new Exception("Key cannot be empty");
        }
        switch (action.toLowerCase()) {
            case "set":
                String value = "";
                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    value += handlers.getDefaultHandler().handle(child, ps, "", attributeNames);
                }
                value = value.trim();
                ps.profile.set(key, value);
                ps.profile.save();
                return "";
            case "get":
                return ps.profile.get(key);
            default:
                throw new Exception("Unsupported profile action");
        }
        
    }

}
