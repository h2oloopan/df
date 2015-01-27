package core.grammar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.grammar.GrammarMatcher;
import play.Logger;
import gram.gravy.GMatcher;
import gram.gravy.GPrecursor;
import gram.gravy.GResult;
import gram.gravy.GTerm;
import gram.util.SpaceType;

public class LaoMaGrammarMatcher implements GrammarMatcher {
	private GPrecursor precursor;
    private GMatcher matcher;	
	
	@Override
	public GrammarMatcher initialize(String path) throws Exception {
		//File grams = new File(new File(path), "execs/grams.bin");
	    File grams = new File(path);
		if (!grams.exists()) {
		    throw new IOException("Grammar file does not exist.");
		} else {
			try {
				precursor = GPrecursor.loadPrecursor(grams);
				matcher = precursor.newMatcher(SpaceType.chinese);
				return this;
			} catch (Exception e) {
				throw e;
			}
		}
	}

	@Override
	public String match(String query) throws Exception {
	    if (query == null) {
	        return null;
	    }
		if (matcher == null) {
			throw new Exception("No matcher exists");
		} else {
			@SuppressWarnings("unchecked")
			Map<String, Float> result = (Map<String, Float>)matcher.match(query);
			Float largest = 0f;
			String output = null;
			for (String key : result.keySet()) {
				Float value = result.get(key);
				if (value >= largest) {
					output = key;
					largest = value;
				}
			}
			if (output != null) {
				return output.trim();
			} else {
				return output;
			}
		}
	}

	
    @Override
    public HashMap<String, String> getMap(String parsedQuery) throws Exception
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(parsedQuery, parsedQuery);
        return map;
    }
    
    @Override
    public String getValue(String input, String term, String key) throws Exception {
        matcher.match(input);
        GResult result = matcher.term(term).parse();
        return formatValue(result.getProperty(key));
    }
    
    private String formatValue(Object o) {
        StringBuffer s = new StringBuffer();
        if (o == null) {
            s.append("null");
        } else if (o instanceof Calendar) {
            s.append(String
                    .format("\"%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS\"", o));
        } else if (o instanceof List) {
            s.append("[");
            for (Object a : (List) o) {
                s.append(formatValue(a)).append(",");
            }
            //delete last comma(,)
            if(!((List) o).isEmpty())
                s.deleteCharAt(s.length() - 1);
            s.append("]");
        } else {
            s.append(o.toString());
        }
        return s.toString();
    }
}
