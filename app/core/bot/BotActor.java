package core.bot;

import grammar.Matcher;

import java.util.Map;

import com.google.inject.Inject;

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
	
	//constructor
	public BotActor(Parser parser, Matcher matcher, String name, String path) throws Exception {
		Logger.info("Initializing bot " + name + " at " + path);
		this.parser = parser;
		this.matcher = matcher;
		this.name = name;
		this.path = path;
		this.topics = new BasicParser().parse(path);
	}
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
