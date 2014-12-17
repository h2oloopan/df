package grammar;

import java.util.Map;

public interface Matcher {
	public void initialize(String path) throws Throwable;
	public Map<String, Float> match(String sentence);
}

