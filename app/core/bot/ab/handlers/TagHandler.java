/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * TagHandler.java
 *
 */
package core.bot.ab.handlers;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 9, 2015
 */
public abstract class TagHandler
{
    public abstract void handle(Node node, ParseState ps);
}
