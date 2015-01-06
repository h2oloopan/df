package core.messages;

import java.util.Date;

import play.libs.Json;

public class Query {
	//Make sure this is immutable
	private final String uid;
	private final String sid;
	
	private final String text;
	private final String topic;
	private final Date timestamp;
	
	public Query(String uid, String sid, String text) {
		this(uid, sid, text, null);
	}
	public Query(String uid, String sid, String text, String topic) {
		this(uid, sid, text, topic, new Date());
	}
	public Query(String uid, String sid, String text, String topic, Date timestamp) {
		this.uid = uid;
		this.sid = sid;
		this.text = text;
		this.topic = topic;
		this.timestamp = timestamp;
	}
	
	public String getUid() {
		return this.uid;
	}
	
	public String getSid() {
		return this.sid;
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
	
	@Override
	public String toString() {
		return Json.stringify(Json.toJson(this));
	}
}
