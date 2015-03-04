package core.grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import play.Logger;
import play.libs.Json;


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
			executor.execute(cmd);
			//compilation is done, now scan for public terms
			ArrayList<String> terms = new ArrayList<String>();
			File grammarFolder = new File(folder, "definition/grammar");
			for (String name : grammarFolder.list()) {
			    File grammar = new File(grammarFolder, name);
			    FileInputStream fis = new FileInputStream(grammar);
			    BufferedReader br = new BufferedReader(new InputStreamReader(fis, "GB18030"));
			    String line;
			    String namespace = "default";
			    while ((line = br.readLine()) != null) {
			        //try to find namespace
			        String pattern = "\\s*namespace\\s+([^\\s]+)\\s*";
			        Pattern np = Pattern.compile(pattern);
			        Matcher nm = np.matcher(line);
			        if (nm.matches()) {
			            namespace = nm.group(1).trim();
			        }
			        
			        pattern = "\\s*public\\s+([^:]+):.+";
			        boolean result = line.matches(pattern);
			        if (result) {
			            Pattern p = Pattern.compile(pattern);
			            Matcher m = p.matcher(line);
			            if (m.matches()) {
			                String publicTerm = m.group(1);
			                terms.add(namespace + "." + publicTerm.trim());
			            }
			        }
			    }
			    br.close();
			}
			//now store all terms to file
			File termsFile = new File(folder, "definition/terms.json");
			termsFile.createNewFile();
			FileUtils.write(termsFile, Json.stringify(Json.toJson(terms)));
			
		}
		catch (IOException e) {
		    Logger.error(e.getMessage(), e);
			throw e;
		}
		
	}
}
