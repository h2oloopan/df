package storage;

import core.context.Context;

public interface ContextProvider {
	public Context getContext();
	public void saveContext(Context context);
}
