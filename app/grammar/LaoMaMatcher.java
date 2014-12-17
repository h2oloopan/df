package grammar;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import gram.gravy.GMatcher;
import gram.gravy.GPrecursor;
import gram.gravy.GResult;
import gram.gravy.GTerm;
import gram.util.SpaceType;
import grammar.Matcher;

public class LaoMaMatcher implements Matcher {
	private String location = "grams/output/grams.bin";
	private GMatcher matcher;

	public LaoMaMatcher() throws Throwable {
		File f = new File(location);
		if (!f.exists()) {
			matcher = null;
		} else {
			GPrecursor precursor;
			try {
				precursor = GPrecursor.loadPrecursor(f);
				matcher = precursor.newMatcher(SpaceType.chinese);
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
	}
	
	@Override
	public Map<String, Float> match(String sentence) {
		return matcher.match(sentence);
	}

}
