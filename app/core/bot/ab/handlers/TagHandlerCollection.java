/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * TagHandlerCollection.java
 *
 */
package core.bot.ab.handlers;

import java.util.HashMap;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 9, 2015
 */
public class TagHandlerCollection extends HashMap<String, TagHandler>
{
    public TagHandlerCollection() {
        super();
        this.put("default", new DefaultHandler());
        this.put("random", new RandomHandler());
        this.put("condition", new ConditionHandler());
        this.put("srai", new SraiHandler());
        this.put("set", new SetHandler());
        this.put("get", new GetHandler());
        this.put("map", new MapHandler());
        this.put("bot", new BotHandler());
        this.put("id", new IdHandler());
        this.put("size", new SizeHandler());
        this.put("vocabulary", new VocabularyHandler());
        this.put("program", new ProgramHandler());
        this.put("date", new DateHandler());
        this.put("interval", new IntervalHandler());
        this.put("think", new ThinkHandler());
        this.put("system", new SystemHandler());
        this.put("explode", new ExplodeHandler());
        this.put("normalize", new NormalizeHandler());
        this.put("denormalize", new DenormalizeHandler());
        this.put("uppercase", new UppercaseHandler());
        this.put("lowercase", new LowercaseHandler());
        this.put("formal", new FormalHandler());
        this.put("sentence", new SentenceHandler());
        this.put("person", new PersonHandler());
        this.put("person2", new Person2Handler());
    }
    
    public TagHandler getHandler(String tag) {
        TagHandler handler = this.get(tag.trim().toLowerCase());
        if (handler == null) {
            return this.get("default");
        } else {
            return handler;
        }
    }
}
