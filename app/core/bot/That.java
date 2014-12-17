package core.bot;

public class That {
	private String content;
	
	public That() {
		this("");
	}
	
	public That(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String c) {
		this.content = c;
	}
	
	@Override
	public String toString() {
		return this.content;
	}
}
