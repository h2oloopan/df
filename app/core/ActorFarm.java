package core;

import java.util.ArrayList;

import akka.actor.ActorRef;

public interface ActorFarm {
	public ActorRef getActor(String name);
	public ArrayList<String> getBots();
}
