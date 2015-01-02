package core.bot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import play.Logger;

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
	private int random(int min, int max) {
		Random rand = new Random();
		int randomInt = rand.nextInt((max - min) + 1) + min;
		return randomInt;
	}
	
	private Category lookup(ArrayList<Category> categories, String pattern, String that) {
		ArrayList<Category> matchPattern = new ArrayList<Category>();
		ArrayList<Category> matchPatternThat = new ArrayList<Category>();
		for (int i = 0; i < categories.size(); i++) {
			Category category = categories.get(i);
			Logger.info(category.toString());
			if (category.getPattern() != null && category.getPattern().toString().equals(pattern)) {
				matchPattern.add(category);
				if (that != null && category.getThat() != null && category.getThat().equals(that)) {
					matchPatternThat.add(category);
				}
			}
		}
		if (matchPatternThat.size() > 0) {
			int index = random(0, matchPatternThat.size() - 1);
			return matchPatternThat.get(index);
		} else if (matchPattern.size() > 0) {
			int index = random(0, matchPattern.size() - 1);
			return matchPattern.get(index);
		} else {
			return null;
		}
	}

	

}
