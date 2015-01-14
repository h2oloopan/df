/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * HandlingHelper.java
 *
 */
package core.bot.ab.handlers;

import java.util.ArrayList;
import java.util.HashSet;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.bot.ab.ParseState;
import core.bot.ab.Utilities;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 14, 2015
 */
public class HandlingHelper
{
    public static String getAttributeOrTagValue (Node node, ParseState ps, String attributeName) throws Exception {
        String result = "";
        Node m = node.getAttributes().getNamedItem(attributeName);
        if (m == null) {
            NodeList childList = node.getChildNodes();
            result = null;         // no attribute or tag named attributeName
            for (int i = 0; i < childList.getLength(); i++)   {
                Node child = childList.item(i);
                if (child.getNodeName().equals(attributeName)) {
                    result = ps.bot.handlers.get("default").handle(child, ps, "",  null);
                }
            }
        }
        else {
            result = m.getNodeValue();
        }
        return result;
    }
    
    public static String condition(Node node, ParseState ps) throws Exception {
        String result="";
        //boolean loop = true;
        NodeList childList = node.getChildNodes();
        ArrayList<Node> liList = new ArrayList<Node>();
        String predicate=null, varName=null, value=null; //Node p=null, v=null;
        HashSet<String> attributeNames = Utilities.stringSet("name", "var", "value");
        // First check if the <condition> has an attribute "name".  If so, get the predicate name.
        predicate = getAttributeOrTagValue(node, ps, "name");
        varName = getAttributeOrTagValue(node, ps, "var");
        // Make a list of all the <li> child nodes:
        for (int i = 0; i < childList.getLength(); i++)
            if (childList.item(i).getNodeName().equals("li")) liList.add(childList.item(i));
        // if there are no <li> nodes, this is a one-shot condition.
        if (liList.size() == 0 && (value = getAttributeOrTagValue(node, ps, "value")) != null   &&
                   predicate != null  &&
                   ps.bot.predicates.get(predicate).equalsIgnoreCase(value))  {
                   return ps.bot.handlers.getHandler("default").handle(node, ps, "", attributeNames);
        }
        else if (liList.size() == 0 && (value = getAttributeOrTagValue(node, ps, "value")) != null   &&
                varName != null  &&
                ps.vars.get(varName).equalsIgnoreCase(value))  {
            return ps.bot.handlers.getHandler("default").handle(node, ps, "", attributeNames);
        }
        // otherwise this is a <condition> with <li> items:
        else for (int i = 0; i < liList.size() && result.equals(""); i++) {
            Node n = liList.get(i);
            String liPredicate = predicate;
            String liVarName = varName;
            if (liPredicate == null) liPredicate = getAttributeOrTagValue(n, ps, "name");
            if (liVarName == null) liVarName = getAttributeOrTagValue(n, ps, "var");
            value = getAttributeOrTagValue(n, ps, "value");
            //System.out.println("condition name="+liPredicate+" value="+value);
            if (value != null) {
                // if the predicate equals the value, return the <li> item.
                if (liPredicate != null && value != null && (ps.bot.predicates.get(liPredicate).equalsIgnoreCase(value) ||
                        (ps.bot.predicates.containsKey(liPredicate) && value.equals("*"))))
                    return ps.bot.handlers.getHandler("default").handle(n, ps, "", attributeNames);
                else if (liVarName != null && value != null && (ps.vars.get(liVarName).equalsIgnoreCase(value) ||
                        (ps.vars.containsKey(liPredicate) && value.equals("*"))))
                    return ps.bot.handlers.getHandler("default").handle(n, ps, "", attributeNames);

           }
            else  // this is a terminal <li> with no predicate or value, i.e. the default condition.
                return ps.bot.handlers.getHandler("default").handle(n, ps, "", attributeNames);
        }
        return "";
    }
}
