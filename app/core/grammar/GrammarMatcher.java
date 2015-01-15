package core.grammar;

import java.util.Map;

public interface GrammarMatcher {
	public GrammarMatcher initialize(String path) throws Exception;
	public String match(String query) throws Exception;
	public HashMap<String, String> getMap(String parsedQuery) throws Exception;
}

