/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * VocabularyHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 15, 2015
 */
public class VocabularyHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        int size = ps.bot.brain.getVocabulary().size();
        return String.valueOf(size);
    }
    
}
