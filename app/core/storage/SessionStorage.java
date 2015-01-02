package core.storage;

import play.libs.F.*;

public interface SessionStorage {
	//A session storage will only act as a key/value pair storage and can only store strings
	//The actually objects must be serialized/deserialized to string by upper level providers
	public String get(final String key) throws Exception;
	public String set(final String key, final String value) throws Exception;
}
