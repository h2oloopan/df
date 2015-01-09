package core.bot;

import core.context.Context;
import core.context.Profile;
import core.grammar.GrammarCompiler;
import core.grammar.Matcher;

import java.util.Map;

import core.messages.CommandType;
import core.messages.Query;
import core.messages.Response;
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
			        String text = query.getText();
		            String topic = query.getTopic();
		            //String pattern = matcher.match(text);
		            //Logger.info("Query: " + text);
		            //Logger.info("Match: " + pattern);
		            
		            Response response = null;
		            Context context = null;
		            Profile profile = null;
		            
		            try {
		                //Need to use context provider to get context here
		                context = contextProvider.getContext(query.getUid(), query.getSid());
		                profile = profileProvider.getProfile(query.getUid());
		                String lastResponse = context.getThat();
		                
		                //Then generate response from the matching category, context, and profile
		                //Category match = finder.find(topics, topic, pattern, that);
		                //Construct a response message
		                //Not sure, but maybe it's the dialog developer's responsibility to provide a default match
		                /*
		                if (match == null) {
		                    Logger.warn("There is no rule to handle given request" + query.toString());
		                    response = new Response(500, "There is no rule to handle given request");
		                } else {
		                    response = new Response(200, match.getTemplate().toString());
		                }
		                */
		            } catch (Exception e) {
		                Logger.error(e.getMessage(), e);
		                response = new Response(500, e.getMessage());
		            }
		            getSender().tell(response, getContext().parent());
		            if (response != null && context != null) {
		                context.insert(query.getText(), response.getText());
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