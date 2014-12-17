package core.bot;

public class Pattern {
	private String content;
	
	public Pattern() {
		this("");
	}
	
	public Pattern(String content) {
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
