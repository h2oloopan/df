/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * UniqHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.bot.ab.Clause;
import core.bot.ab.ParseState;
import core.bot.ab.Tuple;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class UniqHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        return "";
        /*
        HashSet<String> vars = new HashSet<String>();
        HashSet<String> visibleVars = new HashSet<String>();
        String subj = "?subject";
        String pred = "?predicate";
        String obj = "?object";
        NodeList childList = node.getChildNodes();
        for (int j = 0; j < childList.getLength(); j++) {
            Node childNode = childList.item(j);
            String contents = evalTagContent(childNode, ps, null);
            if (childNode.getNodeName().equals("subj")) subj = contents;
            else if (childNode.getNodeName().equals("pred")) pred = contents;
            else if (childNode.getNodeName().equals("obj")) obj = contents;
            if (contents.startsWith("?")) {
                visibleVars.add(contents);
                vars.add(contents);
            }
        }
        Tuple partial = new Tuple(vars, visibleVars);
        Clause clause = new Clause(subj, pred, obj);
        HashSet<Tuple> tuples = ps.chatSession.tripleStore.selectFromSingleClause(partial, clause, true);
        String tupleList = "";
        for (Tuple tuple : tuples) {
            tupleList = tuple.name+" "+tupleList;
        }
        tupleList = tupleList.trim();
        if (tupleList.length()==0) tupleList = "NIL";
        String var = "";
        for (String x : visibleVars) {
           var = x;
        }
        String firstTuple = firstWord(tupleList);
        String result = tupleGet(firstTuple, var);
        return result;
        */
    }

}
