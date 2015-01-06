package core.context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Context {
	public String uid;
	public String sid;
	
	public ArrayList<String> queries;
	public ArrayList<String> responses;
	public HashMap<String, String> map;
	
	public Date dateCreated;
	public Date dateExpired;
	
	public Context(String uid, String sid) {
		this.uid = uid;
		this.sid = sid;
		this.queries = new ArrayList<String>();
		this.responses = new ArrayList<String>();
		this.map = new HashMap<String, String>();
		this.dateCreated = new Date();
	}
	
	public void insert(String query, String response) {
		queries.add(query);
		responses.add(response);
	}
	
	public String getThat() {
		if (responses.size() > 0) {
			return responses.get(responses.size() - 1);
		} else {
			return null;
		}
	}
	
	public String getLastResponse(int index) {
		if (index > responses.size()) {
			return null;
		} else {
			return responses.get(responses.size() - index);
		}
	}
	
	public String getLastQuery(int index) {
		if (index > queries.size()) {
			return null;
		} else {
			return queries.get(queries.size() - index);
		}
	}
}
