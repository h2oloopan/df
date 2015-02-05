
package core;
import static akka.pattern.Patterns.ask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;

import core.bot.BotActor;
import core.grammar.GrammarCompiler;
import core.grammar.GrammarMatcher;
import core.grammar.GrammarMatcherProvider;
import core.messages.CommandType;
import core.messages.Query;
import core.messages.Response;
import core.storage.ContextProvider;
import core.storage.LogProvider;
import core.storage.ProfileProvider;
import play.Logger;
import play.api.libs.Files;
import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Promise;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import akka.actor.*;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Creator;
import akka.japi.Function;
import akka.routing.Broadcast;
import akka.routing.RoundRobinPool;

public class BotActorFarm implements ActorFarm {
	private Map<String, ActorRef> routers;
	private final String rootPath = "bots";
	private final int instances = 4;
	

	private ContextProvider contextProvider;
    private ProfileProvider profileProvider;
    private GrammarCompiler grammarCompiler;
    private GrammarMatcherProvider grammarMatcherProvider;
    private LogProvider logProvider;
	
	@Inject
	public BotActorFarm(ContextProvider contextProvider, ProfileProvider profileProvider, 
            GrammarCompiler grammarCompiler, GrammarMatcherProvider grammarMatcherProvider, LogProvider logProvider) throws Exception {
	    this.contextProvider = contextProvider;
        this.profileProvider = profileProvider;
        this.grammarCompiler = grammarCompiler;
        this.grammarMatcherProvider = grammarMatcherProvider;
        this.logProvider = logProvider;
		initialize();
	}
	
	private void initialize() throws Exception {
		//initialize all bots under root folder
		File root = new File(rootPath);
		String[] bots = root.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		routers = new HashMap<String, ActorRef>();
		
		for (int i = 0; i < bots.length; i++) {
			final String name = bots[i];
			final String path = (new File(root, name)).getCanonicalPath();
			
			final SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create(1, TimeUnit.MINUTES), new Function<Throwable, Directive>() {
				@Override
				public Directive apply(Throwable t) {
					Logger.error(t.getMessage(), t);
					return SupervisorStrategy.escalate();
				}
			});
			
			String gramsPath = (new File(new File(path), "execs/grams.bin")).getCanonicalPath();
		
			ActorRef router = Akka.system().actorOf(
			    new RoundRobinPool(instances).withSupervisorStrategy(strategy).props(
			        BotActor.props(contextProvider, profileProvider, grammarCompiler, grammarMatcherProvider.getMatcher(gramsPath), logProvider, name, path)
			        ), "router-" + name);
			
			
			routers.put(name, router);
			Logger.info("Added bot " + name);
		}
		
	}
	
	@Override
	public ActorRef getActor(String name) {
		return this.routers.get(name);
	}

	@Override
	public ArrayList<String> getBots() {
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(this.routers.keySet());
		return result;
	}

    @Override
    public HashMap<String, String> getGrammars(String name)
    {
        try {
            File root = new File(rootPath);
            String path = (new File(root, name)).getCanonicalPath();
            File folder = new File(new File(path), "definition/grammar");
            HashMap<String, String> result = new HashMap<String, String>();
            for (File entry : folder.listFiles()) {
                if (entry.isFile()) {
                    result.put(entry.getName(), entry.getCanonicalPath());
                }
            }
            return result;
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            return new HashMap<String, String>();
        }
    }

    @Override
    public HashMap<String, String> getAimls(String name)
    {
        try {
            File root = new File(rootPath);
            String path = (new File(root, name)).getCanonicalPath();
            File folder = new File(new File(path), "definition/aiml");
            HashMap<String, String> result = new HashMap<String, String>();
            for (File entry : folder.listFiles()) {
                if (entry.isFile()) {
                    result.put(entry.getName(), entry.getCanonicalPath());
                }
            }
            return result;
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            return new HashMap<String, String>();
        }
    }

    @Override
    public Promise<Exception> reload(String name)
    {
        ActorRef actor = getActor(name);
        Query q = new Query(CommandType.RELOAD);
        return Promise.wrap(ask(actor, new Broadcast(q), 5000)).map(
            new play.libs.F.Function<Object, Exception>() {
                public Exception apply(Object message) {
                    Response response = (Response)message;
                    switch (response.getCode()) {
                    case 200:
                        return null;
                    default:
                        return new Exception(response.getText());
                    }
                }
            }
        );
    }

    @Override
    public String getFile(String path) throws Exception {
        return getFile(path, "UTF-8");
    }
    
    @Override
    public String getFile(String path, String encoding) throws Exception
    {
        Charset charset = Charset.forName(encoding);
        CharsetDecoder decoder = charset.newDecoder();
        
        RandomAccessFile aFile = new RandomAccessFile(path, "r");
        FileChannel inChannel = aFile.getChannel();
        long fileSize = inChannel.size();
        ByteBuffer buffer = ByteBuffer.allocate((int)fileSize);
        inChannel.read(buffer);
        buffer.flip();
        CharBuffer cbuf = decoder.decode(buffer);
        return cbuf.toString();
        
    }
	
}
