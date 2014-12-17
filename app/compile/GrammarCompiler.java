package compile;

import play.libs.F.*;

public interface GrammarCompiler {
	public Promise<Boolean> compile();
}
