package core.bot;

import grammar.Matcher;

import java.util.Map;

import com.google.inject.Inject;

import core.messages.Query;
import play.Logger;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

public class BotActor extends UntypedActor {
	private String name;
	private String path;
	private Map<String, Topic> topics;
	
	private Parser parser;
	private Matcher matcher;
	private Finder finder;
	
	//constructor
	public BotActor(Parser parser, Matcher matcher, Finder finder, String name, String path) throws Exception {
		Logger.info("Initializing bot " + name + " at " + path);
		this.parser = parser;
		this.matcher = matcher;
		this.finder = finder;
		this.name = name;
		this.path = path;
		this.topics = this.parser.parse(path);
		this.matcher.initialize(path);
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if (message instanceof Query) {
			String text = ((Query)message).getText();
			String topic = ((Query)message).getTopic();
			String match = matcher.match(text);
			Logger.info("Query: " + text);
			Logger.info("Match: " + match);
			finder.find(topics, pattern)
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