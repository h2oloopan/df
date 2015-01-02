package core;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class InMemorySessionTable implements SessionTable {
	private final int HOURS = 24;
	private HashMap<String, SessionEntry> table;
	
	private class SessionEntry {
		private String sid;
		private Date expiry;
		
		public SessionEntry(String sid, Date expiry) {
			this.sid = sid;
			this.expiry = expiry;
		}
		
		public String getSid() {
			return sid;
		}
		
		public Date getExpiry() {
			return expiry;
		}
		
		public void setSid(String sid) {
			this.sid = sid;
		}
		
		public void setExpiry(Date expiry) {
			this.expiry = expiry;
		}
	}
	
	public InMemorySessionTable() {
		this.table = new HashMap<String, SessionEntry>();
	}
	
	@Override
	public String getSid(String uid) {
		SessionEntry entry = table.get(uid);
		if (entry == null) {
			String sid = UUID.randomUUID().toString();
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.HOUR, HOURS);
			Date expiry = c.getTime();
			entry = new SessionEntry(sid, expiry);
			table.put(uid, entry);
			return sid;
		} else {
			Date expiry = entry.getExpiry();
			Date now = new Date();
			if (now.after(expiry)) {
				//already expired
				String sid = UUID.randomUUID().toString();
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.HOUR, HOURS);
				expiry = c.getTime();
				entry.setSid(sid);
				entry.setExpiry(expiry);
				return sid;
			} else {
				//refresh expiry date
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.HOUR, HOURS);
				expiry = c.getTime();
				entry.setExpiry(expiry);
				return entry.getSid();
			}
		}
		
	}

}
