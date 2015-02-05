package core;

import java.util.ArrayList;
import java.util.HashMap;

import play.libs.F.Promise;
import akka.actor.ActorRef;

public interface ActorFarm {
	public ActorRef getActor(String name);
	public ArrayList<String> getBots();
	public HashMap<String, String> getGrammars(String name);
	public HashMap<String, String> getAimls(String name);
	public Promise<Exception> reload(String name);
}
