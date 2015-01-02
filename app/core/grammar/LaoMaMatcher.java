package core.grammar;

import java.io.File;
import java.util.Map;

import core.grammar.Matcher;
import play.Logger;
import gram.gravy.GMatcher;
import gram.gravy.GPrecursor;
import gram.util.SpaceType;

public class LaoMaMatcher implements Matcher {
	private GMatcher matcher;

	@Override
	public void initialize(String path) throws Exception {
		File grams = new File(new File(path), "execs/grams.bin");
		Logger.info(grams.getAbsolutePath());
		if (!grams.exists()) {
			matcher = null;
		} else {
			GPrecursor precursor;
			try {
				precursor = GPrecursor.loadPrecursor(grams);
				matcher = precursor.newMatcher(SpaceType.chinese);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	@Override
	public String match(String query) throws Exception {
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
