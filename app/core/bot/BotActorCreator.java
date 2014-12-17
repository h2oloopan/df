package core.bot;

import akka.japi.Creator;

public abstract class BotActorCreator implements Creator<BotActor> {
	public abstract void update(String name, String path);
}
