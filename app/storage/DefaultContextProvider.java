package storage;

import com.google.inject.Inject;

public class DefaultContextProvider implements ContextProvider {
	
	private final SessionStorage sessionStorage;
	
	@Inject
	public DefaultContextProvider(SessionStorage sessionStorage) {
		this.sessionStorage = sessionStorage;
	}
}
