package core.bot;

import core.context.Context;
import core.context.Profile;
import core.grammar.GrammarCompiler;
import core.grammar.GrammarMatcher;

import java.io.File;
import java.util.Map;

import core.messages.CommandType;
import core.messages.Query;
import core.messages.Response;
import core.messages.SpecialText;
import play.Logger;
import core.storage.*;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

public class BotActor extends UntypedActor {
	private String name;
	private String path;
	
	private Bot bot;
	
	private ContextProvider contextProvider;
	private ProfileProvider profileProvider;
	private GrammarCompiler grammarCompiler;
	private GrammarMatcher grammarMatcher;
	private LogProvider logProvider;
	
	//constructor
	public BotActor(ContextProvider contextProvider, ProfileProvider profileProvider, GrammarCompiler grammarCompiler, GrammarMatcher grammarMatcher,
	        LogProvider logProvider, String name, String path) throws Exception {
		Logger.info("Initializing bot " + name + " at " + path);
		this.contextProvider = contextProvider;
		this.profileProvider = profileProvider;
		this.grammarCompiler = grammarCompiler;
		this.grammarMatcher = grammarMatcher;
		this.logProvider = logProvider;
		this.name = name;
		this.path = path;
		this.bot = new Bot(name, path, this.grammarMatcher);
	}
	
	public static Props props(final ContextProvider contextProvider, final ProfileProvider profileProvider, 
	        final GrammarCompiler grammarCompiler, final GrammarMatcher grammarMatcher, final LogProvider logProvider,
	        final String name, final String path) {
	    
	    return Props.create(new Creator<BotActor>() {
	       private static final long serialVersionUID = 1L;
	       
	       @Override
	       public BotActor create() throws Exception {
	           return new BotActor(contextProvider, profileProvider, grammarCompiler, grammarMatcher, logProvider, name, path);
	       }
	    });
	    
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if (message instanceof Query) {
			Query query = (Query)message;
			
			CommandType command = query.getCommand();
			switch (command) {
			    case COMPILE:
			        Response response = null;
			        try {
			            grammarCompiler.compile(path);
			            response = new Response(200, "");
			        } catch (Exception e) {
			            response = new Response(500, e.getMessage());
			        } finally {
			            getSender().tell(response, getContext().parent());
			        }
			        break;
			    case RESPOND:
			        String inputOriginal = query.getText();
		            String topic = query.getTopic();
		            inputOriginal = inputOriginal == null ? SpecialText.NULL : inputOriginal;
		            if (inputOriginal.trim().equals("")) {
		                inputOriginal = SpecialText.NULL;
		            }
		            topic = topic == null ? SpecialText.NULL : topic;
		            Context context = null;
		            Profile profile = null;
		            Logger.info("IO: " + inputOriginal);
		            
		            try {
		                //Need to use context provider to get context here
		                context = contextProvider.getContext(query.getUid(), query.getSid());
		                profile = profileProvider.getProfile(query.getUid());
		                String that = context.getThat();
		                String inputParsed = grammarMatcher.match(inputOriginal);
		                inputParsed = inputParsed == null ? SpecialText.NULL : inputParsed;
		                String output = bot.respond(inputOriginal, inputParsed, that, topic, context, profile);
		                if (output != null) {
		                    //log
		                    logProvider.saveQuery(this.name, topic, inputOriginal, inputParsed, output, query.getUid());
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
		                String lastQuery = inputOriginal != null ? inputOriginal : SpecialText.NULL;
		                String lastResponse = response != null && response.getText() != null ? response.getText() : SpecialText.NULL;
		                context.insert(lastQuery, lastResponse);
		                contextProvider.saveContext(query.getUid(), query.getSid(), context);
		            }
		            
		            if (profile != null) {
		                profileProvider.saveProfile(query.getUid(), profile);
		            }
			        break;
			}
		} else {
			unhandled(message);
		}
	}
}