
package core;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;

import core.bot.BotActor;
import core.grammar.GrammarCompiler;
import core.grammar.GrammarMatcher;
import core.storage.ContextProvider;
import core.storage.LogProvider;
import core.storage.ProfileProvider;
import play.Logger;
import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Promise;
import scala.concurrent.duration.Duration;
import akka.actor.*;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Creator;
import akka.japi.Function;
import akka.routing.RoundRobinPool;

public class BotActorFarm implements ActorFarm {
	private Map<String, ActorRef> routers;
	private final String rootPath = "bots";
	private final int instances = 2;
	

	private ContextProvider contextProvider;
    private ProfileProvider profileProvider;
    private GrammarCompiler grammarCompiler;
    private GrammarMatcher grammarMatcher;
    private LogProvider logProvider;
	
	@Inject
	public BotActorFarm(ContextProvider contextProvider, ProfileProvider profileProvider, 
            GrammarCompiler grammarCompiler, GrammarMatcher grammarMatcher, LogProvider logProvider) throws Exception {
	    this.contextProvider = contextProvider;
        this.profileProvider = profileProvider;
        this.grammarCompiler = grammarCompiler;
        this.grammarMatcher = grammarMatcher;
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
		
			ActorRef router = Akka.system().actorOf(
			    new RoundRobinPool(instances).withSupervisorStrategy(strategy).props(
			        BotActor.props(contextProvider, profileProvider, grammarCompiler, grammarMatcher, logProvider, name, path)
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
	
}
