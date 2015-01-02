package controllers;
import akka.actor.ActorRef;
import static akka.pattern.Patterns.ask;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;

import core.ActorFarm;
import core.SessionTable;
import core.messages.Query;
import play.Logger;
import play.libs.F.*;
import play.mvc.*;

public class Bot extends Controller {
	@Inject
	private ActorFarm farm;
	
	@Inject
	private SessionTable table;
	
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
			if (sid == null) {
				sid = table.getSid(uid);
			}
			
			Query q = new Query(uid, sid, query);
			ActorRef actor = farm.getActor(bot);
			return Promise.wrap(ask(actor, q, 5000)).map(
				new Function<Object, Result>() {
					public Result apply(Object response) {
						return ok(response.toString());
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
