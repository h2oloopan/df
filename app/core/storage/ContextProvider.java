package core.storage;

import core.context.Context;

public interface ContextProvider {
	//uid is user id
	//sid is session id
	public Context getContext(String uid, String sid) throws Exception;
	public void saveContext(String uid, String sid, Context context) throws Exception;
}
