package core.bot;

import akka.japi.Creator;

public class BotActorCreator implements Creator<BotActor> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String path;
	public BotActorCreator(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	@Override
	public BotActor create() throws Exception {
		return new BotActor(name, path);
	}
}