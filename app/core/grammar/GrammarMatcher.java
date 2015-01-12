package core.grammar;

import java.util.Map;

public interface GrammarMatcher {
	public void initialize(String path) throws Exception;
	public String match(String query) throws Exception;
}

