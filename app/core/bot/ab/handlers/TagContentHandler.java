/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * TagContentHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 12, 2015
 */
public class TagContentHandler extends TagHandler
{
    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result = "";
        NodeList childList = node.getChildNodes();
        for (int i = 0; i < childList.getLength(); i++) {
            Node child = childList.item(i);
            if (ignoreAttributes == null || !ignoreAttributes.contains(child.getNodeName())) {
                
            }
        }
    }

}
