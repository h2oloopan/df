package core.grammar;

import java.io.File;
import java.util.Map;

import core.grammar.GrammarMatcher;
import play.Logger;
import gram.gravy.GMatcher;
import gram.gravy.GPrecursor;
import gram.util.SpaceType;

public class LaoMaMatcher implements GrammarMatcher {
	private GMatcher matcher;

	@Override
	public GrammarMatcher initialize(String path) throws Exception {
		File grams = new File(new File(path), "execs/grams.bin");
		if (!grams.exists()) {
			return null;
		} else {
			GPrecursor precursor;
			try {
				precursor = GPrecursor.loadPrecursor(grams);
				matcher = precursor.newMatcher(SpaceType.chinese);
				return this;
			} catch (Exception e) {
				return null;
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
}
