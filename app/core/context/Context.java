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
import core.bot.ab.Clause;
import core.bot.ab.MagicStrings;
import core.bot.ab.Triple;
import core.bot.ab.Tuple;
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
	
	public void printTriples() {
        for (String x : idTriple.keySet()) {
            Triple triple = idTriple.get(x);
            System.out.println(x+":"+triple.subject+":"+triple.predicate+":"+triple.object);
        }
	}
	
	private HashSet<String> emptySet() {
	    return new HashSet<String>();
	}
	
	public HashSet<String> getTriples(String s, String p, String o) {
	    Set<String> subjectSet;
        Set<String> predicateSet;
        Set<String> objectSet;
        Set<String> resultSet;
        //printAllTriples();
        if (s == null || s.startsWith("?")) {
            subjectSet = allTriples();
        }
            else {
                s = s.toUpperCase();
                if (subjectTriples.containsKey(s)) subjectSet = subjectTriples.get(s);
                else subjectSet = emptySet();
            }

        if (p == null || p.startsWith("?")) {
            predicateSet = allTriples();
        }
            else {
                p = p.toUpperCase();
                if (predicateTriples.containsKey(p)) predicateSet = predicateTriples.get(p);
                else predicateSet = emptySet();
            }

        if (o == null || o.startsWith("?")) {
            objectSet = allTriples();
        }
            else {
                o = o.toUpperCase();
                if (objectTriples.containsKey(o)) objectSet = objectTriples.get(o);
                else objectSet = emptySet();
            }

        resultSet = new HashSet(subjectSet);
        resultSet.retainAll(predicateSet);
        resultSet.retainAll(objectSet);

        HashSet<String> finalResultSet = new HashSet(resultSet);


        return finalResultSet;
	}
	
	public HashSet<String> getTripleSubjects(HashSet<String> triples) {
	    HashSet<String> resultSet = new HashSet<String>();
        for (String id : triples) {
            Triple triple = idTriple.get(id);
            resultSet.add(triple.subject);
        }
        return resultSet;
	}
	
	public HashSet<String> getTriplePredicates(HashSet<String> triples) {
	    HashSet<String> resultSet = new HashSet<String>();
        for (String id : triples) {
            Triple triple = idTriple.get(id);
            resultSet.add(triple.predicate);
        }
        return resultSet;
	}
	
	public HashSet<String> getTripleObjects(HashSet<String> triples) {
        HashSet<String> resultSet = new HashSet<String>();
        for (String id : triples) {
            Triple triple = idTriple.get(id);
            resultSet.add(triple.object);
        }
        return resultSet;
	}
	
	public String formatAIMLTripleList(HashSet<String> triples) {
        String result = MagicStrings.default_list_item;//"NIL"
        for (String x : triples) {
            result = x+" "+result;//"CONS "+x+" "+result;
        }
        return result.trim();
	}
	
	public String getTripleSubject(String id) {
        if (idTriple.containsKey(id)) return idTriple.get(id).subject;
        else return "Unknown subject";
    }
    public String getTriplePredicate(String id) {
        if (idTriple.containsKey(id)) return idTriple.get(id).predicate;
        else return "Unknown predicate";
    }
    public String getTripleObject(String id) {
        if (idTriple.containsKey(id)) return idTriple.get(id).object;
        else return "Unknown object";
    }
    public String stringTriple(String id) {
        Triple triple = idTriple.get(id);
        return id+" "+triple.subject+" "+triple.predicate+" "+triple.object;
    }
    public void printAllTriples () {
        for (String id : idTriple.keySet()) {
            System.out.println(stringTriple(id));
        }
    }
    
    public HashSet<Tuple> selectTuple(HashSet<String> vars, HashSet<String> visibleVars, ArrayList<Clause> clauses) {
        HashSet<Tuple> result = new HashSet<Tuple>();
        try {

            Tuple tuple = new Tuple(vars, visibleVars);
            result = selectFromRemainingClauses(tuple, clauses);
        }
        catch (Exception ex) {
            System.out.println("Something went wrong with select "+visibleVars);
            ex.printStackTrace();

        }
        return result;
    }
	
    public Clause adjustClause(Tuple tuple, Clause clause) {
        Set vars = tuple.getVars();
        String subj = clause.subj; String pred = clause.pred; String obj = clause.obj;
        Clause newClause = new Clause(clause);
        if (vars.contains(subj)) {
            String value = tuple.getValue(subj);
            if (!value.equals(MagicStrings.unbound_variable)) {/*System.out.println("adjusting "+subj+" "+value);*/ newClause.subj = value;}
        }
        if (vars.contains(pred)) {
            String value = tuple.getValue(pred);
            if (!value.equals(MagicStrings.unbound_variable)) {/*System.out.println("adjusting "+pred+" "+value);*/ newClause.pred= value;}
        }
        if (vars.contains(obj)) {
            String value = tuple.getValue(obj);
            if (!value.equals(MagicStrings.unbound_variable)) {/*System.out.println("adjusting "+obj+" "+value); */newClause.obj = value;}
        }
        return newClause;
    }
    
    public Tuple bindTuple(Tuple partial, String triple, Clause clause) {
        Tuple tuple = new Tuple(partial);
        if (clause.subj.startsWith("?")) tuple.bind(clause.subj, getTripleSubject(triple));
        if (clause.pred.startsWith("?")) tuple.bind(clause.pred, getTriplePredicate(triple));
        if (clause.obj.startsWith("?")) tuple.bind(clause.obj, getTripleObject(triple));
        return tuple;
    }
    
    public HashSet<Tuple> selectFromSingleClause(Tuple partial, Clause clause, Boolean affirm) {
        HashSet<Tuple> result = new HashSet<Tuple>();
        HashSet<String> triples = getTriples(clause.subj, clause.pred, clause.obj);
        //System.out.println("TripleStore: selected "+triples.size()+" from single clause "+clause.subj+" "+clause.pred+" "+clause.obj);
        if (affirm) {
            for (String triple : triples) {
                Tuple tuple = bindTuple(partial, triple, clause);
                result.add(tuple);
            }
        }
        else {
            if (triples.size()==0) result.add(partial);
        }
        return result;
    }
    
    public HashSet<Tuple> selectFromRemainingClauses(Tuple partial, ArrayList<Clause> clauses) {
        //System.out.println("TripleStore: partial = "+partial.printTuple()+" clauses.size()=="+clauses.size());
        HashSet<Tuple> result = new HashSet<Tuple>();
        Clause clause = clauses.get(0);
        clause = adjustClause(partial, clause);
        HashSet<Tuple> tuples = selectFromSingleClause(partial, clause, clause.affirm);
        if (clauses.size() > 1) {
          ArrayList<Clause> remainingClauses = new ArrayList<Clause>(clauses);
          remainingClauses.remove(0);
          for (Tuple tuple : tuples) {
             result.addAll(selectFromRemainingClauses(tuple, remainingClauses));
          }
        }
        else result = tuples;
        return result;
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
