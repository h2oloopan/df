package grammar;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.*;

import com.jgoodies.common.base.SystemUtils;

public class LaoMaGrammarCompiler implements GrammarCompiler {
	@Override
	public void compile(String path) throws Exception {
		try {
			File folder = new File(path);
			File file = new File(folder, "build.linux.xml");
			if (SystemUtils.IS_OS_WINDOWS) {
				file = new File(folder, "build.windows.xml");
			}
			String buildFile = file.getCanonicalPath();
			CommandLine cmd = new CommandLine("ant");
			cmd.addArgument("-buildfile " + buildFile);
			
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(0);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
			executor.setWatchdog(watchdog);
			int exitValue = executor.execute(cmd);
		}
		catch (IOException e) {
			throw e;
		}
		
	}
}
