/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * DateHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;
import core.bot.ab.utils.CalendarUtils;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class DateHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String jformat = HandlingHelper.getAttributeOrTagValue(node, ps, "jformat");      // AIML 2.0
        String locale = HandlingHelper.getAttributeOrTagValue(node, ps, "locale");
        String timezone = HandlingHelper.getAttributeOrTagValue(node, ps, "timezone");
        String dateAsString = CalendarUtils.date(jformat, locale, timezone);
        return dateAsString;
    }

}
