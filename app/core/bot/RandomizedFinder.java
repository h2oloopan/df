package core.bot;

import java.util.ArrayList;
import java.util.Map;

public class RandomizedFinder implements Finder {


	@Override
	public Category find(Map<String, Topic> topics, String topic, String pattern) {
		return find(topics, topic, pattern, null);
	}

	@Override
	public Category find(Map<String, Topic> topics, String topic,
			String pattern, String that) {
		ArrayList<Category> categories = new ArrayList<Category>();
		if (topic == null || topics.get(topic) == null) {
			for (Topic t : topics.values()) {
				categories.addAll(t.getCategories());
			}
		} else {
			categories.addAll(topics.get(topic).getCategories());
			for (String name : topics.keySet()) {
				if (!name.equals(topic)) {
					categories.addAll(topics.get(name).getCategories());
				}
			}
		}
		return lookup(categories, pattern, that);
	}
	
	//PRIVATE STUFF
	private Category lookup(ArrayList<Category> categories, String pattern, String that) {
		
	}

	

}
