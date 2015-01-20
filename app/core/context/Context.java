package core.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.libs.Json;
import core.bot.Bot;
import core.bot.ab.MagicStrings;
import core.bot.ab.Triple;
import core.messages.SpecialText;

public class Context {
	public String uid;
	public String sid;
	
	public ArrayList<String> queries;
	public ArrayList<String> responses;
	public HashMap<String, String> predicates;
	public HashMap<String, String> vars;
	
	//TRIPLES
	public HashMap<String, Triple> idTriple;
	public HashMap<String, String> tripleStringId;
	public HashMap<String, HashSet<String>> subjectTriples;
	public HashMap<String, HashSet<String>> predicateTriples;
	public HashMap<String, HashSet<String>> objectTriples;
	private int idCnt = 0;
	
	
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
		this.vars = new HashMap<String, String>();
		this.dateCreated = new Date();
		
		idCnt = 0;
		
		//TRIPLES
		this.idTriple = new HashMap<String, Triple>();
		this.tripleStringId = new HashMap<String, String>();
		this.subjectTriples = new HashMap<String, HashSet<String>>();
		this.predicateTriples = new HashMap<String, HashSet<String>>();
		this.objectTriples = new HashMap<String, HashSet<String>>();
	}
	
	public void initialize(Bot bot) throws Exception {
	    String predicatesPath = new File(new File(bot.config_path), "predicates.txt").getCanonicalPath();
	    addPredicates(predicatesPath);
	}
	
	//TRIPLE related functions
	public String mapTriple(Triple triple) {
	    String id = triple.id;
        idTriple.put(id, triple);
        String s, p, o;
        s = triple.subject;
        p = triple.predicate;
        o = triple.object;

        s = s.toUpperCase();
        p = p.toUpperCase();
        o = o.toUpperCase();

        String tripleString = s+":"+p+":"+o;
        tripleString = tripleString.toUpperCase();

        if (tripleStringId.keySet().contains(tripleString)) {
            return tripleStringId.get(tripleString); // triple already exists
        }
        else {
            tripleStringId.put(tripleString, id);

            HashSet<String> existingTriples;
            if (subjectTriples.containsKey(s)) existingTriples = subjectTriples.get(s);
            else existingTriples = new HashSet<String>();
            existingTriples.add(id);
            subjectTriples.put(s, existingTriples);

            if (predicateTriples.containsKey(p)) existingTriples = predicateTriples.get(p);
            else existingTriples = new HashSet<String>();
            existingTriples.add(id);
            predicateTriples.put(p, existingTriples);

            if (objectTriples.containsKey(o)) existingTriples = objectTriples.get(o);
            else existingTriples = new HashSet<String>();
            existingTriples.add(id);
            objectTriples.put(o, existingTriples);

            return id;
        }
	}
	
	public String unMapTriple(Triple triple) {
	    String id = MagicStrings.undefined_triple;
        String s, p, o;
        s = triple.subject;
        p = triple.predicate;
        o = triple.object;

        s = s.toUpperCase();
        p = p.toUpperCase();
        o = o.toUpperCase();

        String tripleString = s+":"+p+":"+o;

        System.out.println("unMapTriple "+tripleString);
        tripleString = tripleString.toUpperCase();


        triple = idTriple.get(tripleStringId.get(tripleString));

        System.out.println("unMapTriple "+triple);
        if (triple != null) {
        id = triple.id;
        idTriple.remove(id);
        tripleStringId.remove(tripleString);

        HashSet<String> existingTriples;
        if (subjectTriples.containsKey(s)) existingTriples = subjectTriples.get(s);
        else existingTriples = new HashSet<String>();
        existingTriples.remove(id);
        subjectTriples.put(s, existingTriples);

        if (predicateTriples.containsKey(p)) existingTriples = predicateTriples.get(p);
        else existingTriples = new HashSet<String>();
        existingTriples.remove(id);
        predicateTriples.put(p, existingTriples);

        if (objectTriples.containsKey(o)) existingTriples = objectTriples.get(o);
        else existingTriples = new HashSet<String>();
        existingTriples.remove(id);
        objectTriples.put(o, existingTriples);
        }
        else id = MagicStrings.undefined_triple;

        return id;
	}
	
	public Set<String> allTriples() {
	    return new HashSet<String>(idTriple.keySet());
	}
	
	public String addTriple(String subject, String predicate, String object) {
	    if (subject == null || predicate == null || object == null) return MagicStrings.undefined_triple;
	    String tid = uid + "_" + sid + idCnt++;
	    Triple triple = new Triple(tid, subject, predicate, object);
	    String id = mapTriple(triple);
	    return id;
	}
	
	public String deleteTriple(String subject, String predicate, String object) {
	    if (subject == null || predicate == null || object == null) return MagicStrings.undefined_triple;
	    String tid = uid + "_" + sid + idCnt++;
        Triple triple = new Triple(tid, subject, predicate, object);
        String id = unMapTriple(triple);
        return id;
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
