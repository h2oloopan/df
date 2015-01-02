package core.bot;

import core.context.Context;
import core.grammar.Matcher;

import java.util.Map;

import core.messages.Query;
import play.Logger;
import core.storage.*;
import akka.actor.UntypedActor;

public class BotActor extends UntypedActor {
	private String name;
	private String path;
	private Map<String, Topic> topics;
	
	private Parser parser;
	private Matcher matcher;
	private Finder finder;
	private Processor processor;
	private ContextProvider contextProvider;
	private ProfileProvider profileProvider;
	
	//constructor
	public BotActor(Parser parser, Matcher matcher, Finder finder, Processor processor, ContextProvider contextProvider, ProfileProvider profileProvider, 
			String name, String path) throws Exception {
		Logger.info("Initializing bot " + name + " at " + path);
		this.parser = parser;
		this.matcher = matcher;
		this.finder = finder;
		this.processor = processor;
		this.contextProvider = contextProvider;
		this.profileProvider = profileProvider;
		this.name = name;
		this.path = path;
		this.topics = this.parser.parse(path);
		this.matcher.initialize(path);
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if (message instanceof Query) {
			Query query = (Query)message;
			String text = query.getText();
			String topic = query.getTopic();
			String pattern = matcher.match(text);
			Logger.info("Query: " + text);
			Logger.info("Match: " + pattern);
			Category match = finder.find(topics, topic, pattern);
			
			//Need to use context provider to get context here
			Context context = contextProvider.getContext(query.getUid(), query.getSid());
			
			//Construct a response message
			
			/*
			if (match == null) {
				getSender().tell("null", getContext().parent()); //thus "null" is a reserved word, it is used to return when there is no match what so ever
			} else {
				getSender().tell(match, getContext().parent());
			}
			*/
		} else {
			unhandled(message);
		}
	}
}