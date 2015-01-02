package core.bot;

import com.google.inject.Inject;

import core.grammar.Matcher;
import core.storage.*;
import akka.japi.Creator;


public class BasicBotActorCreator extends BotActorCreator {
	private static final long serialVersionUID = 1L;
	private String name;
	private String path;
	private Parser parser;
	private Matcher matcher;
	private Finder finder;
	private Processor processor;
	private ContextProvider contextProvider;
	private ProfileProvider profileProvider;
	
	@Inject
	public BasicBotActorCreator(Parser parser, Matcher matcher, Finder finder, Processor processor, ContextProvider contextProvider, ProfileProvider profileProvider) {
		this.parser = parser;
		this.matcher = matcher;
		this.finder = finder;
		this.processor = processor;
		this.contextProvider = contextProvider;
		this.profileProvider = profileProvider;
	}
	
	@Override
	public BotActor create() throws Exception {
		return new BotActor(parser, matcher, finder, processor, contextProvider, profileProvider, name, path);
	}

	@Override
	public void update(String name, String path) {
		this.name = name;
		this.path = path;
	}
}