/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * AddTripleHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class AddTripleHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        return "";
        /*
        String subject = HandlingHelper.getAttributeOrTagValue(node, ps, "subj");
        String predicate = HandlingHelper.getAttributeOrTagValue(node, ps, "pred");
        String object = HandlingHelper.getAttributeOrTagValue(node, ps, "obj");
        return ps.bot.tripleStore.addTriple(subject, predicate, object);
        */ // THE triples should probably be moved into context
    }

}
