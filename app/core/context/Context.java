package core.context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import play.libs.Json;
import core.messages.SpecialText;

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
	    return getLastResponse(0);
	}
	
	public String getLastResponse(int index) {
		if (index >= responses.size()) {
			return SpecialText.NULL;
		} else {
			return responses.get(responses.size() - index - 1);
		}
	}
	
	public String getLastQuery(int index) {
		if (index >= queries.size()) {
			return SpecialText.NULL;
		} else {
			return queries.get(queries.size() - index - 1);
		}
	}
	
	@Override
	public String toString() {
	    return Json.stringify(Json.toJson(this));
	}
}
