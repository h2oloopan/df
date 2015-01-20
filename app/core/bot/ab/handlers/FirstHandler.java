/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * FirstHandler.java
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
public class FirstHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String content = handlers.getDefaultHandler().handle(node, ps, "", null);
        return firstWord(content);
    }
    
    private String firstWord(String sentence) {
        String content = (sentence == null ? "" : sentence);
        content = content.trim();
        if (content.contains(" ")) {
            String head = content.substring(0, content.indexOf(" "));
            return head;
        }
        else if (content.length() > 0) return content;
        else return MagicStrings.default_list_item;
    }

}
