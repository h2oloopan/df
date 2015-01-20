/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * IntervalHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;
import core.bot.ab.utils.CalendarUtils;
import core.bot.ab.utils.IntervalUtils;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class IntervalHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String style = HandlingHelper.getAttributeOrTagValue(node, ps, "style", handlers);      // AIML 2.0
        String jformat = HandlingHelper.getAttributeOrTagValue(node, ps, "jformat", handlers);      // AIML 2.0
        String from = HandlingHelper.getAttributeOrTagValue(node, ps, "from", handlers);
        String to = HandlingHelper.getAttributeOrTagValue(node, ps, "to", handlers);
        if (style == null) style = "years";
        if (jformat == null) jformat = "MMMMMMMMM dd, yyyy";
        if (from == null) from = "January 1, 1970";
        if (to == null) {
            to = CalendarUtils.date(jformat, null, null);
        }
        String result = "unknown";
        if (style.equals("years")) result = ""+IntervalUtils.getYearsBetween(from, to, jformat);
        if (style.equals("months")) result = ""+IntervalUtils.getMonthsBetween(from, to, jformat);
        if (style.equals("days")) result = ""+IntervalUtils.getDaysBetween(from, to, jformat);
        if (style.equals("hours")) result = ""+IntervalUtils.getHoursBetween(from, to, jformat);
        return result;
    }

}
