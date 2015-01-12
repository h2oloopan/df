package core.bot;

import com.google.inject.Inject;

import core.grammar.GrammarCompiler;
import core.grammar.GrammarMatcher;
import core.storage.*;
import akka.japi.Creator;


public class BasicBotActorCreator extends BotActorCreator {
	private static final long serialVersionUID = 1L;
	private String name;
	private String path;
	private ContextProvider contextProvider;
	private ProfileProvider profileProvider;
	private GrammarCompiler grammarCompiler;
	private GrammarMatcher matcher;
	
	@Inject
	public BasicBotActorCreator(ContextProvider contextProvider, ProfileProvider profileProvider, GrammarCompiler grammarCompiler, GrammarMatcher matcher) {
		this.contextProvider = contextProvider;
		this.profileProvider = profileProvider;
		this.grammarCompiler = grammarCompiler;
		this.matcher = matcher;
	}
	
	@Override
	public BotActor create() throws Exception {
		return new BotActor(contextProvider, profileProvider, grammarCompiler, matcher, name, path);
	}

	@Override
	public void update(String name, String path) {
		this.name = name;
		this.path = path;
	}
}