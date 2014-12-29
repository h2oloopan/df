package grammar;

import java.io.File;
import java.util.Map;

import gram.gravy.GMatcher;
import gram.gravy.GPrecursor;
import gram.util.SpaceType;
import grammar.Matcher;

public class LaoMaMatcher implements Matcher {
	private GMatcher matcher;

	@Override
	public void initialize(String path) throws Exception {
		File grams = new File(new File(path), "execs/grams.bin");
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
	public String match(String query) {
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
		return output;
	}
}
