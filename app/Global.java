import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import core.ActorFarm;
import core.BotActorFarm;
import core.bot.*;
import storage.*;
import grammar.*;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {
	private Injector injector;
	
	@Override
	public void onStart(Application application) {
		injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(GrammarCompiler.class).to(LaoMaGrammarCompiler.class).in(Singleton.class);
				bind(SessionStorage.class).to(RedisSessionStorage.class).in(Singleton.class);
				bind(Matcher.class).to(LaoMaMatcher.class).in(Singleton.class);
				bind(ActorFarm.class).to(BotActorFarm.class).in(Singleton.class);
				bind(Parser.class).to(BasicParser.class).in(Singleton.class);
				bind(Finder.class).to(RandomizedFinder.class).in(Singleton.class);
				bind(Processor.class).to(BasicProcessor.class).in(Singleton.class);
				bind(BotActorCreator.class).to(BasicBotActorCreator.class).in(Singleton.class);
			}
		});
		//start bot actors
		
	}
	
	public Injector getInjector() {
		return this.injector;
	}
	
	@Override
	public <T> T getControllerInstance(Class<T> aClass) throws Exception {
		return injector.getInstance(aClass);
	}
}
