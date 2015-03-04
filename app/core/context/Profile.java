package core.context;

import java.util.Date;
import java.util.HashMap;

import core.storage.ProfileProvider;
import play.libs.Json;

public class Profile {
	public String uid;
	public HashMap<String, String> map;
	public Date createdDate;
	
	private ProfileProvider provider;
	
	public Profile() {
	    //do not use this one
	}
	
	public Profile(String uid, ProfileProvider provider) {
		this(uid, new HashMap<String, String>(), new Date(), provider);
	}
	
	public Profile(String uid, HashMap<String, String> map, Date createdDate, ProfileProvider provider) {
		this.uid = uid;
		this.map = map;
		this.createdDate = createdDate;
		this.provider = provider;
	}
	
	public void save() throws Exception {
	    provider.saveProfile(uid, this);
	}
	
	public void set(String key, String value) {
	    map.put(key, value);
	}
	
	public String get(String key) {
	    return map.get(key);
	}
	
	@Override
	public String toString() {
	    return Json.stringify(Json.toJson(this));
	}
	
}
