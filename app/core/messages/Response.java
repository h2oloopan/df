package core.messages;

import java.util.Date;

public class Response {
	private final String text;
	private final Date timestamp;
	
	public Response(String text) {
		this(text, new Date());
	}
	
	public Response(String text, Date timestamp) {
		this.text = text;
		this.timestamp = timestamp;
	}
	
	public String getText() {
		return text;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
}
