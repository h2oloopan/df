/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * DefaultHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 9, 2015
 */
public class DefaultHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String nodeName = node.getNodeName();
        
        if (nodeName.equals("#text")) {
            return node.getNodeValue();
        } else if (nodeName.equals("#comment")) {
            return "";
        } else {
            String result = "";
            NodeList childList = node.getChildNodes();
            for (int i = 0; i < childList.getLength(); i++) {
                Node child = childList.item(i);
                if (ignoreAttributes == null || !ignoreAttributes.contains(child.getNodeName()) {
                    result += this.handle(child, ps, previousResult, ignoreAttributes)
                })
            }
        }
    }

}
