package core.grammar;

import play.libs.F.*;

public interface GrammarCompiler {
	//public Promise<Boolean> compile();
	//public Promise<Boolean> compile(String path);
	public void compile(String path) throws Exception;
}
