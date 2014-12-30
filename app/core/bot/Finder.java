package core.bot;

import java.util.Map;

public interface Finder {
	//if there is no associated topic, you need to pass in null for topic
	public Category find(Map<String, Topic> topics, String topic, String pattern);
	public Category find(Map<String, Topic> topics, String topic, String pattern, String that);
}
