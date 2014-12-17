package core;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import core.bot.BotActorCreator;
import play.libs.Akka;
import akka.actor.*;
import akka.routing.RoundRobinPool;

public class BotActorFarm implements ActorFarm {
	private Map<String, ActorRef> routers;
	private final String rootPath = "bots";
	private final int instances = 1;

	public BotActorFarm() throws IOException {
		//initialize all bots under root folder
		File root = new File(rootPath);
		String[] bots = root.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		routers = new HashMap<String, ActorRef>();
		for (int i = 0; i < bots.length; i++) {
			String name = bots[i];
			String path = (new File(root, name)).getCanonicalPath();
			ActorRef router = Akka.system().actorOf(new RoundRobinPool(instances).props(Props.create(new BotActorCreator(name, path))), "router-" + name);
			routers.put(name, router);
		}
	}
	
	@Override
	public ActorRef getActor(String name) {
		return this.routers.get(name);
	}
	
}
