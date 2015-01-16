package core.storage;

import play.Logger;
import play.libs.Json;

import com.google.inject.Inject;

import core.context.Context;

public class DefaultContextProvider implements ContextProvider {
	
	private final SessionStorage sessionStorage;
	
	@Inject
	public DefaultContextProvider(SessionStorage sessionStorage) {
		this.sessionStorage = sessionStorage;
	}

	@Override
	public Context getContext(String uid, String sid) throws Exception {
		String key = uid + '_' + sid;
		String value = sessionStorage.get(key);
		if (value == null) {
			//create a new context
			Context context = new Context(uid, sid);
			sessionStorage.set(key, Json.stringify(Json.toJson(context)));
			return context;
		} else {
		    Logger.info(value);
			return Json.fromJson(Json.parse(value), Context.class);
		}
	}

	@Override
	public void saveContext(String uid, String sid, Context context) throws Exception {
		String key = uid + '_' + sid;
		String value = Json.stringify(Json.toJson(context));
		sessionStorage.set(key, value);
	}
}
