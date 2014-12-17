package controllers;


import grammar.GrammarCompiler;

import com.google.inject.Inject;

import play.libs.F.*;
import play.mvc.*;

public class Grammar extends Controller {
	@Inject
	private GrammarCompiler compiler;
}
