package storage;

import play.libs.F.*;

public interface SessionStorage {
	//A session storage will only act as a key/value pair storage and can only store strings
	//The actually objects must be serialized/deserialized to string by upper level providers
	public Promise<Either<String, Throwable>> get(final String key);
	public Promise<Either<String, Throwable>> set(final String key, final String value);
}
