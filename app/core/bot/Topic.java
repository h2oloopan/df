package core.bot;

import java.util.ArrayList;

import play.Logger;

public class Topic {
	private String name;
	private ArrayList<Category> categories;
	
	public Topic() {
		this("null"); //if there is no topic, we default it to null topic, so null is indeed reserved here
	}
	
	public Topic(String name) {
		this.name = name;
		this.categories = new ArrayList<Category>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addCategory(Category c) {
		this.categories.add(c);
	}
	
	@Override
	public String toString() {
		String output = "Topic[" + this.name + "] Categories\r\n";
		
		for (int i = 0; i < categories.size(); i++) {
			Category c = categories.get(i);
			output += "[" + i + "] " + categories.get(i).toString();
		}
		
		return output;
	}
}
