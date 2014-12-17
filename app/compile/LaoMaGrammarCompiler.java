package compile;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.*;

import com.jgoodies.common.base.SystemUtils;

import play.libs.F.Promise;
import play.libs.F.*;
import akka.dispatch.*;

public class LaoMaGrammarCompiler implements GrammarCompiler {
	private class ResultHandler extends DefaultExecuteResultHandler {		
		private scala.concurrent.Promise<Boolean> promise;
		public ResultHandler(scala.concurrent.Promise<Boolean> promise) {
			this.promise = promise;
		}
		
		@Override
		public void onProcessComplete(final int exitValue) {
			super.onProcessComplete(exitValue);
			promise.success(true);
		}
		
		@Override
		public void onProcessFailed(final ExecuteException e) {
			super.onProcessFailed(e);
			promise.failure(e);
		}
	}
	@Override
	public Promise<Boolean> compile() {		
		final scala.concurrent.Promise<Boolean> promise = Futures.promise();
		
		try{
			File file = new File("grams/build.xml");
			String path = file.getCanonicalPath();
			CommandLine cmd = new CommandLine("ant");
			cmd.addArgument("-buildfile " + path);
			ResultHandler resultHandler = new ResultHandler(promise);
			PumpStreamHandler streamHandler = new PumpStreamHandler();
			ShutdownHookProcessDestroyer processDestroyer = new ShutdownHookProcessDestroyer();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setStreamHandler(streamHandler);
			executor.setProcessDestroyer(processDestroyer);
			executor.execute(cmd, resultHandler);
		}
		catch (IOException e) {
			promise.failure(e);
		}
		
		return Promise.wrap(promise.future());
	}
	@Override
	public Promise<Boolean> compile(String path) {
		final scala.concurrent.Promise<Boolean> promise = Futures.promise();
		
		try {
			File folder = new File(path);
			File file = new File(folder, "build.linux.xml");
			if (SystemUtils.IS_OS_WINDOWS) {
				file = new File(folder, "build.windows.xml");
			}
			String buildFile = file.getCanonicalPath();
			CommandLine cmd = new CommandLine("ant");
			cmd.addArgument("-buildfile " + buildFile);
			ResultHandler resultHandler = new ResultHandler(promise);
			PumpStreamHandler streamHandler = new PumpStreamHandler();
			ShutdownHookProcessDestroyer processDestroyer = new ShutdownHookProcessDestroyer();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setStreamHandler(streamHandler);
			executor.setProcessDestroyer(processDestroyer);
			executor.execute(cmd, resultHandler);
		}
		catch (IOException e) {
			promise.failure(e);
		}
		
		return Promise.wrap(promise.future());
	}
}
