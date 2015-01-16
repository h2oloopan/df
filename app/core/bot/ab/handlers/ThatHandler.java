/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ThatHandler.java
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
public class ThatHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        int index=0;
        int jndex=0;
        String value = HandlingHelper.getAttributeOrTagValue(node, ps, "index");
        if (value != null)
            try {
                String pair = value;
                String[] spair = pair.split(",");
                index = Integer.parseInt(spair[0])-1;
                jndex = Integer.parseInt(spair[1])-1;
                System.out.println("That index="+index+","+jndex);
            } catch (Exception ex) { ex.printStackTrace(); }
        String that = MagicStrings.unknown_history_item;
        //History hist = ps.chatSession.thatHistory.get(index);
        //if (hist != null) that = (String)hist.get(jndex);
        that = ps.context.getLastResponse(jndex);
        return that.trim();
    }

}
