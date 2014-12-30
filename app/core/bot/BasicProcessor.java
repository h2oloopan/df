package core.bot;

import core.context.Context;

public class BasicProcessor implements Processor {

	@Override
	public String process(Category category, Context context) {
		return category.getTemplate().getContent();
	}

}
