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
}
