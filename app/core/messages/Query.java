package core.messages;

import java.util.Date;

public class Query {
	//Make sure this is immutable
	private final String text;
	private final String topic;
	private final Date timestamp;
	public Query(String text) {
		this(text, null);
	}
	public Query(String text, String topic) {
		this(text, topic, new Date());
	}
	public Query(String text, String topic, Date timestamp) {
		this.text = text;
		this.topic = topic;
		this.timestamp = timestamp;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public Date getTimestamp() {
		return this.timestamp;
	}
}
