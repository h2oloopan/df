package core.messages;

import java.util.Date;

public class Response {
	private final String text;
	private final Date timestamp;
	private final int code;
	
	public Response(int code, String text) {
		this(code, text, new Date());
	}
	
	public Response(int code, String text, Date timestamp) {
		this.code = code;
		this.text = text;
		this.timestamp = timestamp;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getText() {
		return text;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
}
