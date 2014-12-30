package core.bot;

import java.util.ArrayList;
import java.util.Map;

public class RandomizedFinder implements Finder {

	@Override
	public Category find(Map<String, Topic> topics, String pattern) {
		return find(topics, pattern, null); //send it to the complete one without specifying the topic
	}

	@Override
	public Category find(Map<String, Topic> topics, String pattern, String that) {
		ArrayList<Category> categories = new ArrayList<Category>();
		for (Topic t : topics.values()) {
			categories.addAll(t.getCategories());
		}
		return lookup(categories, pattern, that);
	}

	@Override
	public Category findWithTopic(Map<String, Topic> topics, String topic,
			String pattern) {
		Topic match = topics.get(topic);
		ArrayList<Category> categories;
		
		if (match == null) {
			categories = new ArrayList<Category>();
			for (Topic t : topics.values()) {
				categories.addAll(t.getCategories());
			}
		} else {
			categories = match.getCategories();
		}
	}

	@Override
	public Category findWithTopic(Map<String, Topic> topics, String topic,
			String pattern, String that) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//PRIVATE STUFF
	private Category lookup(ArrayList<Category> categories, String pattern, String that) {
		
	}

}
