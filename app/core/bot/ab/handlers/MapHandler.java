/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * MapHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.AIMLMap;
import core.bot.ab.MagicStrings;
import core.bot.ab.ParseState;
import core.bot.ab.Utilities;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 15, 2015
 */
public class MapHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result = MagicStrings.default_map;
        HashSet<String> attributeNames = Utilities.stringSet("name");
        String mapName = HandlingHelper.getAttributeOrTagValue(node, ps, "name", handlers);
        String contents = handlers.getDefaultHandler().handle(node.getFirstChild(), ps, "", attributeNames);
        contents = contents.trim();
        if (mapName == null) result = "<map>"+contents+"</map>"; // this is an OOB map tag (no attribute)
        else {
            AIMLMap map = ps.bot.mapMap.get(mapName);
            if (map != null) {
                result = map.get(contents.toUpperCase());
            }
            if (result == null) {
                result = MagicStrings.default_map;
            }
            result = result.trim();
        }
        return result;
    }

}
