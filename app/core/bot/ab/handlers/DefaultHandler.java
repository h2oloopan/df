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

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 9, 2015
 */
public class DefaultHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult)
    {
        return unevaluatedXML(previousResult, node, ps);
    }

    @Override
    public boolean hasRecursion()
    {
        return true;
    }

    @Override
    public Set<String> getIgnoredAttributes()
    {
        return null;
    }
    
    private String unevaluatedXML(String resultIn, Node node, ParseState ps) {
        String nodeName = node.getNodeName();
        String attributes = "";
        if (node.hasAttributes()) {
            NamedNodeMap XMLAttributes = node.getAttributes();
            for(int i=0; i < XMLAttributes.getLength(); i++)
            {
                attributes += " "+XMLAttributes.item(i).getNodeName()+"=\""+XMLAttributes.item(i).getNodeValue()+"\"";
            }
        }
        String result = "<"+nodeName+attributes+"/>";
        if (! resultIn.equals(""))
            result = "<"+nodeName+attributes+">"+resultIn+"</"+nodeName+">";
        return result;
    }

}
