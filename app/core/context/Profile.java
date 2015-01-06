package core.context;

import java.util.Date;
import java.util.HashMap;

public class Profile {
	public String uid;
	public String name;
	public HashMap<String, String> map;
	
	public Date createdDate;
	
	public Profile(String uid) {
		this.uid = uid;
		this.map = new HashMap<String, String>();
		this.createdDate = new Date();
	}
	
	public Profile(String uid, String name, HashMap<String, String> map) {
		this.uid = uid;
		this.name = name;
		this.map = map;
	}
	
}
