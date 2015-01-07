package core.bot;

public class Pattern {
	private String grammar;
	private String regular;
	
	public Pattern() {
		this(null, null);
	}
	
	public Pattern(String grammar, String regular) {
	    this.grammar = grammar;
	    this.regular = regular;
	}
	
	public String getGrammar() {
	    return grammar;
	}
	
	public String getRegular() {
	    return regular;
	}
	
	public void setGrammar(String grammar) {
	    this.grammar = grammar;
	}
	
	public void setRegular(String regular) {
	    this.regular = regular;
	}
	
	public void setPattern(String grammar, String regular) {
	    this.grammar = grammar;
	    this.regular = regular;
	}
	
	@Override
	public String toString() {
		return "Grammar: " + (grammar != null ? grammar : "NULL") + " Regular: " + (regular != null ? regular : "NULL");
	}
	
	//originalQuery is the text we obtained from client without any modification
	//parsedQuery is the result we get from grammar
	public boolean match(String originalQuery, String parsedQuery) {
		
	}
}
