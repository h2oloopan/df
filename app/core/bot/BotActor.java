package core.bot;

import java.util.Map;

import play.Logger;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

public class BotActor extends UntypedActor {
	private String name;
	private String path;
	private Map<String, Topic> topics;
	
	//constructor
	public BotActor(String name, String path) throws Exception {
		Logger.info("Initializing bot " + name + " at " + path);
		this.name = name;
		this.path = path;
		this.topics = new Parser().parse(path);
	}
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
