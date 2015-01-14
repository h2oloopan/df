/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * DefaultHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import play.Logger;
import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 9, 2015
 * This DefaultHandler actually does text and template node
 */
public class ConditionHandler extends TagHandler
{   
    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        boolean loop = true;
        String result = "";
        int loopCnt = 0;
        while (loop && loopCnt < MagicNumbers.max_loops) {
            String loopResult = condition(node, ps);
            if (loopResult.trim().equals(MagicStrings.too_much_recursion)) {
                return MagicStrings.too_much_recursion;
            }
            if (loopResult.contains("<loop/>")) {
                loopResult = loopResult.replace("<loop/>", "");
                loop = true;
            }
            else {
                loop = false;
            }
            result += loopResult;
        }
        if (loopCnt >= MagicNumbers.max_loops) result = MagicStrings.too_much_recursion;
    }

}
