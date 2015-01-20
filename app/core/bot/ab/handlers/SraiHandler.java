/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SraiHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ComprehensiveProcessor;
import core.bot.ab.MagicStrings;
import core.bot.ab.Nodemapper;
import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class SraiHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String response = MagicStrings.default_bot_response;
        
        String result = handlers.getDefaultHandler().handle(node, ps, "", null);
        result = result.trim();
        result = result.replaceAll("(\r\n|\n\r|\r|\n)", " ");
        result = ps.bot.preProcessor.normalize(result);
        String topic = ps.context.retrievePredicate("topic");     // the that stays the same, but the topic may have changed
        Nodemapper leaf = ps.bot.brain.match(result, ps.that, topic);
        if (leaf == null) {
            return(response);
        }
        response = ComprehensiveProcessor.evalTemplate(leaf.category.getTemplate(), 
            new ParseState(ps.depth+1, ps.inputOriginal, ps.inputParsed, ps.that, topic, leaf, ps.bot, ps.context, ps.profile));
        
        result = response.trim();
        return result;
    }

}
