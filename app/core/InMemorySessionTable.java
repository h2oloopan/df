package core;

import java.util.HashMap;

public class InMemorySessionTable implements SessionTable {

	private HashMap<String, String> table;
	
	public InMemorySessionTable() {
		this.table = new HashMap<String, String>();
	}
	
	@Override
	public String getSid(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
