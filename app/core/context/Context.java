package core.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.libs.Json;
import core.bot.Bot;
import core.bot.ab.MagicStrings;
import core.messages.SpecialText;

public class Context {
	public String uid;
	public String sid;
	
	public ArrayList<String> queries;
	public ArrayList<String> responses;
	public HashMap<String, String> predicates;
	
	public Date dateCreated;
	public Date dateExpired;
	
	public Context() {
	    //Do not use this one
	}
	
	public Context(String uid, String sid) {
		this.uid = uid;
		this.sid = sid;
		this.queries = new ArrayList<String>();
		this.responses = new ArrayList<String>();
		this.predicates = new HashMap<String, String>();
		this.dateCreated = new Date();
	}
	
	public void initialize(Bot bot) throws Exception {
	    String predicatesPath = new File(new File(bot.config_path), "predicates.txt").getCanonicalPath();
	    addPredicates(predicatesPath);
	}
	
	//PREDICATES related functions
	public void addPredicates(String path) throws Exception {
	    getPredicateDefaults(path);
	    addPredicate("topic", MagicStrings.default_topic);
	}
	
	public String addPredicate(String key, String value) {
        if (key.equals("topic") && value.length() == 0) {
            value = MagicStrings.default_get;
        }
        if (value.equals(MagicStrings.too_much_recursion)) {
            value = MagicStrings.default_list_item;
        }

        String result = predicates.put(key, value);
        return result;
	}
	
	public String retrievePredicate(String key) {
	    String result = predicates.get(key);
	    if (result == null) {
	        result = MagicStrings.default_get;
	    }
	    return result;
	}
	
	public void getPredicateDefaultsFromInputStream (InputStream in) throws Exception {
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        
        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            if (strLine.contains(":")) {
                String property = strLine.substring(0, strLine.indexOf(":"));
                String value = strLine.substring(strLine.indexOf(":")+1);
                addPredicate(property, value);
            }
        }
	}
	
	public void getPredicateDefaults (String filename) throws Exception {
        File file = new File(filename);
        if (file.exists()) {
            FileInputStream fstream = new FileInputStream(filename);
            // Get the object
            getPredicateDefaultsFromInputStream(fstream);
            fstream.close();
        }
	}
	
	/////
	
	public void insert(String query, String response) {
		queries.add(query);
		responses.add(response);
	}
	
	@JsonIgnore
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
