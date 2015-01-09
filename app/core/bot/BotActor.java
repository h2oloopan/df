package core.bot;

import core.context.Context;
import core.context.Profile;
import core.grammar.GrammarCompiler;
import core.grammar.Matcher;

import java.util.Map;

import core.messages.CommandType;
import core.messages.Query;
import core.messages.Response;
import core.messages.SpecialText;
import play.Logger;
import core.storage.*;
import akka.actor.UntypedActor;

public class BotActor extends UntypedActor {
	private String name;
	private String path;
	
	private Bot bot;
	
	private ContextProvider contextProvider;
	private ProfileProvider profileProvider;
	private GrammarCompiler grammarCompiler;
	
	//constructor
	public BotActor(ContextProvider contextProvider, ProfileProvider profileProvider, GrammarCompiler grammarCompiler, 
	        String name, String path) throws Exception {
		Logger.info("Initializing bot " + name + " at " + path);
		this.bot = new Bot(name, path);
		this.contextProvider = contextProvider;
		this.profileProvider = profileProvider;
		this.grammarCompiler = grammarCompiler;
		this.name = name;
		this.path = path;
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if (message instanceof Query) {
			Query query = (Query)message;
			
			CommandType command = query.getCommand();
			switch (command) {
			    case COMPILE:
			        grammarCompiler.compile(path);
			        break;
			    case RESPOND:
			        String input = query.getText();
		            String topic = query.getTopic();
		            Response response = null;
		            Context context = null;
		            Profile profile = null;
		            
		            try {
		                //Need to use context provider to get context here
		                context = contextProvider.getContext(query.getUid(), query.getSid());
		                profile = profileProvider.getProfile(query.getUid());
		                String that = context.getThat();
		                String output = bot.respond(input, that, topic, context, profile);
		                if (output != null) {
		                    response = new Response(200, output);
		                } else {
		                    response = new Response(500, "Cannot respond to the query for some reason");
		                }
		            } catch (Exception e) {
		                Logger.error(e.getMessage(), e);
		                response = new Response(500, e.getMessage());
		            }
		            getSender().tell(response, getContext().parent());
		            //update context and profile
		            if (context != null) {
		                String lastQuery = query != null && query.getText() != null ? query.getText() : SpecialText.NULL;
		                String lastResponse = response != null && response.getText() != null ? response.getText() : SpecialText.NULL;
		                context.insert(lastQuery, lastResponse);
		                contextProvider.saveContext(query.getUid(), query.getSid(), context);
		            }
		            profileProvider.saveProfile(query.getUid(), profile);
			        break;
			}
		} else {
			unhandled(message);
		}
	}
}