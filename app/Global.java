import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import core.*;
import core.bot.*;
import core.grammar.*;
import core.storage.*;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {
	private Injector injector;
	
	@Override
	public void onStart(Application application) {
		injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(SessionStorage.class).to(RedisSessionStorage.class);
				bind(GrammarMatcher.class).to(LaoMaGrammarMatcher.class);
				bind(ContextProvider.class).to(DefaultContextProvider.class);
				bind(ProfileProvider.class).to(MySqlProfileProvider.class);
				bind(LogProvider.class).to(MySqlLogProvider.class);
				bind(GrammarCompiler.class).to(LaoMaGrammarCompiler.class).in(Singleton.class);
				bind(ActorFarm.class).to(BotActorFarm.class).in(Singleton.class);
				bind(SessionTable.class).to(InMemorySessionTable.class).in(Singleton.class);
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
