package grammar;

import java.util.Map;

public interface Matcher {
	public Map<String, Float> match(String sentence);
}

