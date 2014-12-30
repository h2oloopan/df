package core.bot;

import core.context.Context;

public interface Processor {
	public String process(Category category, Context context);
}
