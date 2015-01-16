/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SelectHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.bot.ab.ParseState;
import core.bot.ab.Tuple;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class SelectHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        return "";
        /*
        
        ArrayList<Clause> clauses = new ArrayList<Clause>();
        NodeList childList = node.getChildNodes();
        //String[] splitTuple;
        HashSet<String> vars = new HashSet<String>();
        HashSet<String> visibleVars = new HashSet<String>();
        for (int i = 0; i < childList.getLength(); i++) {
            Node childNode = childList.item(i);
            if (childNode.getNodeName().equals("vars")) {
                String contents = ps.bot.handlers.getHandler("default").handle(childNode, ps, "", null);
                String [] splitVars = contents.split(" ");
                for (String var : splitVars) {
                    var = var.trim();
                    if (var.length() > 0) visibleVars.add(var);
                }
               // System.out.println("AIML Processor select: visible vars "+visibleVars);
            }

            else if (childNode.getNodeName().equals("q") || childNode.getNodeName().equals("notq")) {
                Boolean affirm = !childNode.getNodeName().equals("notq");
                NodeList grandChildList = childNode.getChildNodes();
                String subj = null;
                String pred = null;
                String obj = null;
                for (int j = 0; j < grandChildList.getLength(); j++) {
                    Node grandChildNode = grandChildList.item(j);
                    String contents = ps.bot.handlers.getHandler("default").handle(grandChildNode, ps, "", null);
                    if (grandChildNode.getNodeName().equals("subj")) subj = contents;
                    else if (grandChildNode.getNodeName().equals("pred")) pred = contents;
                    else if (grandChildNode.getNodeName().equals("obj")) obj = contents;
                    if (contents.startsWith("?")) vars.add(contents);

                }
                Clause clause = new Clause(subj, pred, obj, affirm);
                //System.out.println("Vars "+vars+" Clause "+subj+" "+pred+" "+obj+" "+affirm);
                clauses.add(clause);

            }
        }
        HashSet<Tuple> tuples = ps.chatSession.tripleStore.select(vars, visibleVars, clauses);
        String result = "";
        for (Tuple tuple : tuples) {
            result = tuple.name+" "+result;
        }
        result = result.trim();
        if (result.length()==0) result = "NIL";
        return result;
        */
        //TODO: we need to fix tuples first to make this even working
    }

}
