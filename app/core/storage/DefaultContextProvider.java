package core.storage;

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
		return Json.fromJson(Json.parse(value), Context.class);
	}

	@Override
	public void saveContext(String uid, String sid, Context context) throws Exception {
		String key = uid + '_' + sid;
		String value = Json.stringify(Json.toJson(context));
		sessionStorage.set(key, value);
	}
}
