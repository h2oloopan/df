/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * RestHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.MagicStrings;
import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class RestHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String content = handlers.getDefaultHandler().handle(node, ps, "", null);
        content = ps.bot.preProcessor.normalize(content);
        return restWords(content);
    }
    
    private String restWords(String sentence) {
        String content = (sentence == null ? "" : sentence);
        content = content.trim();
        if (content.contains(" ")) {
            String tail = content.substring(content.indexOf(" ")+1, content.length());
            return tail;
        }
        else return MagicStrings.default_list_item;
    }
}
