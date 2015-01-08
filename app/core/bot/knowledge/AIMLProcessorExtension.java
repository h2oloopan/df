/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * AIMLProcessorExtension.java
 *
 */
package core.bot.knowledge;

import org.w3c.dom.Node;

import java.util.Set;


/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 8, 2015
 */
public interface AIMLProcessorExtension
{
    /**
     * provide the AIMLProcessor with a list of extension tag names.
     *
     * @return      Set of extension tag names
     */
   public Set<String> extensionTagSet();

    /**
     * recursively evaluate AIML from a node corresponding an extension tag
     *
     * @param node                current XML parse node
     * @param ps                  current parse state
     * @return                    result of evaluating AIML
     */
   public String recursEval(Node node, ParseState ps);
}
