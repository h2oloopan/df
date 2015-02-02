package controllers;
import java.util.ArrayList;
import java.util.UUID;

import akka.actor.ActorRef;
import static akka.pattern.Patterns.ask;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;

import core.ActorFarm;
import core.SessionTable;
import core.messages.CommandType;
import core.messages.Query;
import core.messages.Response;
import play.Logger;
import play.libs.Json;
import play.libs.F.*;
import play.mvc.*;

public class Bot extends Controller {
	@Inject
	private ActorFarm farm;
	@Inject
	private SessionTable table;

	//List all available bots
	public Promise<Result> bots() {
		try {
		    final ArrayList<String> bots = farm.getBots();
		    return Promise.promise(new Function0<Result>() {
		       public Result apply() {
		           return ok(Json.toJson(bots));
		       }
		    });
		} catch (final Exception e) {
			return Promise.promise(new Function0<Result>() {
				public Result apply() {
					return badRequest(e.getMessage());
				}
			});
		}
	}

	//Compile grammar files for a bot
	public Promise<Result> compile() {
		try {
			JsonNode json = request().body().asJson();
			String bot = json.findPath("bot").textValue();
			Logger.info("Compiling bot " + bot);
			ActorRef actor = farm.getActor(bot);
			Query q = new Query(CommandType.COMPILE);
			return Promise.wrap(ask(actor, q, 5000)).map(
			    new Function<Object, Result>() {
			        public Result apply(Object message) {
			            Response response = (Response)message;
                        switch (response.getCode()) {
                        case 200:
                            return ok();
                        case 500:
                            return internalServerError(response.getText());
                        default:
                            return ok();
                        }
			        }
			    }
			);
		} catch (final Exception e) {
			return Promise.promise(new Function0<Result>() {
				public Result apply() {
					return badRequest(e.getMessage());
				}
			});
		}
	}
	
	public Promise<Result> talk() {
		try {
			JsonNode json = request().body().asJson();
			String bot = json.findPath("bot").textValue();
			String query = json.findPath("query").textValue();
			String uid = json.findPath("uid").textValue();
			String sid = json.findPath("sid").textValue();
			Logger.info("Asking bot " + bot + " for query " + query);
			
			//Create actor message here
			//Get uid and sid
			if (uid == null) {
				uid = UUID.randomUUID().toString();
			}
			
			if (sid == null) {
				sid = table.getSid(uid);
			}
			
			Query q = new Query(CommandType.RESPOND, uid, sid, query);
			ActorRef actor = farm.getActor(bot);
			return Promise.wrap(ask(actor, q, 5000)).map(
				new Function<Object, Result>() {
					public Result apply(Object message) {
						Response response = (Response)message;
						switch (response.getCode()) {
						case 200:
							return ok(Json.toJson(response));
						case 500:
							return internalServerError(response.getText());
						default:
							return ok(Json.toJson(response));
						}
					}
				}
			);
		} catch (final Exception e) {
			return Promise.promise(new Function0<Result>() {
				public Result apply() {
					return badRequest(e.getMessage());
				}
			});
		}
	}
}
