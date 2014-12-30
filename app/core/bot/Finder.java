package core.bot;

import java.util.Map;

public interface Finder {
	public Category find(Map<String, Topic> topics, String pattern);
	public Category find(Map<String, Topic> topics, String pattern, String that);
	public Category findWithTopic(Map<String, Topic> topics, String topic, String pattern);
	public Category findWithTopic(Map<String, Topic> topics, String topic, String pattern, String that);
}
