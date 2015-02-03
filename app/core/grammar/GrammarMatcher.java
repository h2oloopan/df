package core.grammar;

import java.util.HashMap;
import java.util.Map;

public interface GrammarMatcher {
	public String match(String query) throws Exception;
	public HashMap<String, String> getMap(String parsedQuery) throws Exception;
	public String getValue(String input, String term, String key) throws Exception;
}

