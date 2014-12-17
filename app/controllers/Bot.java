package controllers;
import com.google.inject.Inject;

import core.ActorFarm;
import play.libs.F.*;
import play.mvc.*;

public class Bot extends Controller {
	@Inject
	private ActorFarm farm;
	
	public Result talk() {
		return ok();
	}
}
