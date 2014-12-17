package core.bot;

public class Template {
	private String content;
	
	public Template() {
		this("");
	}
	
	public Template(String content) {
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
