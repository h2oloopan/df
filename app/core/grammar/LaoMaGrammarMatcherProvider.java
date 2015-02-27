/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * LaoMaGrammarMatcherProvider.java
 *
 */
package core.grammar;

import gram.gravy.GPrecursor;
import gram.util.SpaceType;

import java.io.File;
import java.io.IOException;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Feb 3, 2015
 */
public class LaoMaGrammarMatcherProvider implements GrammarMatcherProvider
{

    @Override
    public GrammarMatcher getMatcher(String gPath, String tPath) throws Exception
    {
        return new LaoMaGrammarMatcher(gPath, tPath);
    }

}
