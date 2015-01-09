package core.bot;

import com.google.inject.Inject;

import core.grammar.GrammarCompiler;
import core.grammar.Matcher;
import core.storage.*;
import akka.japi.Creator;


public class BasicBotActorCreator extends BotActorCreator {
	private static final long serialVersionUID = 1L;
	private String name;
	private String path;
	private ContextProvider contextProvider;
	private ProfileProvider profileProvider;
	private GrammarCompiler grammarCompiler;
	
	@Inject
	public BasicBotActorCreator(ContextProvider contextProvider, ProfileProvider profileProvider, GrammarCompiler grammarCompiler) {
		this.contextProvider = contextProvider;
		this.profileProvider = profileProvider;
		this.grammarCompiler = grammarCompiler;
	}
	
	@Override
	public BotActor create() throws Exception {
		return new BotActor(contextProvider, profileProvider, grammarCompiler, name, path);
	}

	@Override
	public void update(String name, String path) {
		this.name = name;
		this.path = path;
	}
}