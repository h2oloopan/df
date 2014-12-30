package core.bot;

import java.util.Map;

public interface Finder {
	public Category find(Map<String, Topic> topics, String pattern);
	public Category find(Map<String, Topic> topics, String pattern, String topic);
}
