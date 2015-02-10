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
	public String getFile(String path) throws Exception;
	public String getFile(String path, String encoding) throws Exception;
	public void updateFile(String path, String text) throws Exception;
	public void updateFile(String path, String text, String encoding) throws Exception;
	public void removeFile(String path) throws Exception;
	public String createFile(String bot, String name, String type, String text) throws Exception; //return path
}
