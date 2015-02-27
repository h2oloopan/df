/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * GrammarMatcherProvider.java
 *
 */
package core.grammar;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Feb 3, 2015
 */
public interface GrammarMatcherProvider
{
    public GrammarMatcher getMatcher(String gPath, String tPath) throws Exception; 
}
