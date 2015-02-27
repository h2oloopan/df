/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * GrammarMatch.java
 *
 */
package match;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.Logger;
import core.grammar.GrammarMatcher;
import core.grammar.LaoMaGrammarMatcher;
import core.grammar.LaoMaGrammarMatcherProvider;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class LaoMaGrammarMatchTest
{
    private GrammarMatcher matcher;
    
    @Before
    public void setup() {
        try {
            String grammarsPath = new File("test/supply/execs/grams.bin").getCanonicalPath();
            String termsPath = new File("test/supply/terms.json").getCanonicalPath();
            this.matcher = new LaoMaGrammarMatcherProvider().getMatcher(grammarsPath, termsPath);
        } catch (Exception e) {
            Assert.fail("Setup: should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void canMatch1() {
        System.out.println("Testing a possible match");
        String query = "你好吗";
        try {
            String result = matcher.match(query);
            Assert.assertEquals("default.你好吗", result);
            query = "你好不好";
            Assert.assertEquals("default.你好吗", result);
            query = "你怎么样";
            Assert.assertEquals("default.你好吗", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void cannotMatch1() {
        System.out.println("Testing without a possible match");
        String query = "你不好吗";
        try {
            String result = matcher.match(query);
            Assert.assertEquals(null, result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
