package core.bot;

import com.google.inject.Inject;

import grammar.Matcher;
import akka.japi.Creator;

public class BasicBotActorCreator extends BotActorCreator {
	private static final long serialVersionUID = 1L;
	private String name;
	private String path;
	private Parser parser;
	private Matcher matcher;
	
	@Inject
	public BasicBotActorCreator(Parser parser, Matcher matcher) {
		this.parser = parser;
		this.matcher = matcher;
	}
	
	@Override
	public BotActor create() throws Exception {
		return new BotActor(parser, matcher, name, path);
	}

	@Override
	public void update(String name, String path) {
		this.name = name;
		this.path = path;
	}
}