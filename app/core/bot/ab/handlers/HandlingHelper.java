/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * HandlingHelper.java
 *
 */
package core.bot.ab.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.bot.ab.MagicStrings;
import core.bot.ab.ParseState;
import core.bot.ab.Tuple;
import core.bot.ab.Utilities;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 14, 2015
 */
public class HandlingHelper
{
    
    public static String handleChildren(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes, TagHandlerCollection handlers) throws Exception {
        String result = "";
        NodeList childList = node.getChildNodes();
        for (int i = 0; i < childList.getLength(); i++) {
            Node child = childList.item(i);
            if (ignoreAttributes == null || !ignoreAttributes.contains(child.getNodeName())) {
                result += handlers.getDefaultHandler().handle(child, ps, previousResult, ignoreAttributes);
            }
        }
        return result;
    }
    
    public static String capitalizeString(String str) {
        char[] chars = str.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i])) {
                found = false;
            }
        }
        return String.valueOf(chars);
    }
    
    public static int getIndexValue(Node node, ParseState ps, TagHandlerCollection handlers) throws Exception {
        int index=0;
        String value = getAttributeOrTagValue(node, ps, "index", handlers);
        if (value != null) try {index = Integer.parseInt(value)-1;} catch (Exception ex) {throw ex;}
        return index;
    }
    
    public static String tupleGet(String tupleName, String varName) throws Exception {
        String result = MagicStrings.default_get;
        Tuple tuple = Tuple.tupleMap.get(tupleName);
        //System.out.println("Tuple = "+tuple.printTuple());
        //System.out.println("Value = "+tuple.getValue(varName));
        if (tuple == null) result = MagicStrings.default_get;
        else result = tuple.getValue(varName);
        return result;
    }
    
    public static String getAttributeOrTagValue (Node node, ParseState ps, String attributeName, TagHandlerCollection handlers) throws Exception {
        String result = "";
        Node m = node.getAttributes().getNamedItem(attributeName);
        if (m == null) {
            NodeList childList = node.getChildNodes();
            result = null;         // no attribute or tag named attributeName
            for (int i = 0; i < childList.getLength(); i++)   {
                Node child = childList.item(i);
                if (child.getNodeName().equals(attributeName)) {
                    result = handlers.getDefaultHandler().handle(child, ps, "",  null);
                }
            }
        }
        else {
            result = m.getNodeValue();
        }
        return result;
    }
    
    public static String condition(Node node, ParseState ps, TagHandlerCollection handlers) throws Exception {
        String result="";
        //boolean loop = true;
        NodeList childList = node.getChildNodes();
        ArrayList<Node> liList = new ArrayList<Node>();
        String predicate=null, varName=null, value=null; //Node p=null, v=null;
        HashSet<String> attributeNames = Utilities.stringSet("name", "var", "value");
        // First check if the <condition> has an attribute "name".  If so, get the predicate name.
        predicate = getAttributeOrTagValue(node, ps, "name", handlers);
        varName = getAttributeOrTagValue(node, ps, "var", handlers);
        // Make a list of all the <li> child nodes:
        for (int i = 0; i < childList.getLength(); i++) {
            if (childList.item(i).getNodeName().equals("li")) {
                liList.add(childList.item(i));
            }
        }
        // if there are no <li> nodes, this is a one-shot condition.
        if (liList.size() == 0 && (value = getAttributeOrTagValue(node, ps, "value", handlers)) != null   &&
                   predicate != null  &&
                   ps.context.retrievePredicate(predicate).equalsIgnoreCase(value))  {
                   return handlers.getDefaultHandler().handle(node.getFirstChild(), ps, "", attributeNames);
        }
        else if (liList.size() == 0 && (value = getAttributeOrTagValue(node, ps, "value", handlers)) != null   &&
                varName != null  &&
                ps.vars.get(varName).equalsIgnoreCase(value))  {
            return handlers.getDefaultHandler().handle(node.getFirstChild(), ps, "", attributeNames);
        }
        else for (int i = 0; i < liList.size() && result.equals(""); i++) {
            Node n = liList.get(i);
            String liPredicate = predicate;
            String liVarName = varName;
            if (liPredicate == null) liPredicate = getAttributeOrTagValue(n, ps, "name", handlers);
            if (liVarName == null) liVarName = getAttributeOrTagValue(n, ps, "var", handlers);
            value = getAttributeOrTagValue(n, ps, "value", handlers);
            //System.out.println("condition name="+liPredicate+" value="+value);
            if (value != null) {
                // if the predicate equals the value, return the <li> item.
                if (liPredicate != null && value != null && (ps.context.retrievePredicate(liPredicate).equalsIgnoreCase(value) ||
                        (ps.context.predicates.containsKey(liPredicate) && value.equals("*"))))
                    return handlers.getHandler("default").handle(n, ps, "", attributeNames);
                else if (liVarName != null && value != null && (ps.vars.get(liVarName).equalsIgnoreCase(value) ||
                        (ps.vars.containsKey(liPredicate) && value.equals("*"))))
                    return handlers.getHandler("default").handle(n, ps, "", attributeNames);

           }
            else  // this is a terminal <li> with no predicate or value, i.e. the default condition.
                return handlers.getHandler("default").handle(n, ps, "", attributeNames);
        }
        return "";
    }
}
