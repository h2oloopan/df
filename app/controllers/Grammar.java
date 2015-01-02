package controllers;


import com.google.inject.Inject;

import core.grammar.GrammarCompiler;
import play.libs.F.*;
import play.mvc.*;

public class Grammar extends Controller {
	@Inject
	private GrammarCompiler compiler;
}
