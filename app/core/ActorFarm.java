package core;

import akka.actor.ActorRef;

public interface ActorFarm {
	public ActorRef getActor(String name);
}
