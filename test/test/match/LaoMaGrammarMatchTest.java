/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * GrammarMatch.java
 *
 */
package test.match;

import org.junit.Test;

import core.grammar.GrammarMatcher;
import core.grammar.LaoMaGrammarMatcher;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class LaoMaGrammarMatchTest
{
    private GrammarMatcher matcher;
    
    public LaoMaGrammarMatchTest() throws Exception {
        String path = "";
        this.matcher = new LaoMaGrammarMatcher().initialize(path);
    }
    
    @Test
    public void canMatch1() {
        
    }
    
    @Test
    public void cannotMatch1() {
        
    }
}
