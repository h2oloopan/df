/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * DefaultHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.ArrayList;
import java.util.Set;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import play.Logger;
import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 9, 2015
 * This DefaultHandler actually does text and template node
 */
public class RandomHandler extends TagHandler
{   
    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        NodeList childList = node.getChildNodes();
        ArrayList<Node> liList = new ArrayList<Node>();
        String setName = getAttributeOrTagValue(node, ps, "set");
        for (int i = 0; i < childList.getLength(); i++) {
            if (childList.item(i).getNodeName().equals("li")) {
                liList.add(childList.item(i));
            }
        }
        int index = (int) (Math.random() * liList.size());
        return ps.bot.handlers.getHandler("default").handle(liList.get(index), ps, "", null)
    }

}
