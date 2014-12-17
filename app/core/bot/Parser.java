package core.bot;

import java.util.HashMap;

public interface Parser {
	public HashMap<String, Topic> parse(String path) throws Exception;
}
