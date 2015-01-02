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
}
