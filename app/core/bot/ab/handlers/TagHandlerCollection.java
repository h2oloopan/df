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
        this.put("vocabulary", new VocabularyHandler()); //no need to unit test this
        this.put("program", new ProgramHandler()); //no need to unit test this
        this.put("date", new DateHandler()); //no need to unit test this for now
        this.put("interval", new IntervalHandler());
        this.put("think", new ThinkHandler());
        //this.put("system", new SystemHandler()); //should not implement this for now as it could lead to security reasons
        this.put("explode", new ExplodeHandler());
        //this.put("normalize", new NormalizeHandler()); //This need to be tweaked
        //this.put("denormalize", new DenormalizeHandler()); //We are not supporting normalization and denormalization for now
        this.put("uppercase", new UppercaseHandler());
        this.put("lowercase", new LowercaseHandler());
        this.put("formal", new FormalHandler());
        this.put("sentence", new SentenceHandler());
        //this.put("person", new PersonHandler()); //Alright, we are not using person or person2 at the moment (they are not useful for Chinese either).
        //this.put("person2", new Person2Handler());
        //this.put("gender", new GenderHandler());
        this.put("star", new InputStarHandler());
        this.put("thatstar", new ThatStarHandler());
        this.put("topicstar", new TopicStarHandler());
        
        this.put("that", new ThatHandler());
        //this.put("input", new InputHandler()); //don't break down into sentences at the moment
        this.put("request", new RequestHandler());
        this.put("response", new ResponseHandler());
        //this.put("addtriple", new AddTripleHandler());
        //this.put("deletetriple", new DeleteTripleHandler());
        //this.put("select", new SelectHandler());
        //this.put("uniq", new UniqHandler());
        //this.put("first", new FirstHandler()); //these are mostly only useful for English words
        //this.put("rest", new RestHandler()); //same as above
        
        //GRAMMAR related tags <- custom tag = not in aiml specs
        this.put("gval", new GrammarValueHandler());
        this.put("log", new LogHandler());
        this.put("cqa", new CQAHandler());
    }
    
    public TagHandler getDefaultHandler() {
        return this.get("default").initialize(this);
    }
    
    public TagHandler getHandler(String tag) {
        TagHandler handler = this.get(tag.trim().toLowerCase());
        if (handler == null) {
            return this.get("default").initialize(this);
        } else {
            return handler.initialize(this);
        }
    }
}
