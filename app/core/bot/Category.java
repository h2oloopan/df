package core.bot;

public class Category {
	private Pattern pattern;
	private That that;
	private Template template;

	public Pattern getPattern() {
		return this.pattern;
	}
	
	public void setPattern(Pattern p) {
		this.pattern = p;
	}
	
	public That getThat() {
		return this.that;
	}
	
	public void setThat(That t) {
		this.that = t;
	}
	
	public Template getTemplate() {
		return this.template;
	}
	
	public void setTemplate(Template t) {
		this.template = t;
	}
	
	@Override
	public String toString() {
		return "Pattern: " + this.pattern == null ? "null" : this.pattern.toString() 
				+ " That: " + this.that == null? "null" : this.that.toString() 
				+ " Template: " + this.template == null? "null" : this.template.toString();
	}
	
}
