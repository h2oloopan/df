package core.storage;

import core.context.Profile;

public interface ProfileProvider {
	public Profile getProfile(String uid) throws Exception;
	public void saveProfile(String uid, Profile profile) throws Exception;
}
