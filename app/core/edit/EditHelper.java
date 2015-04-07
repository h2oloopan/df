/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * EditHelper.java
 *
 */
package core.edit;

import java.io.File;
import java.util.HashMap;

import play.Logger;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Apr 7, 2015
 */
public class EditHelper
{
    public static HashMap<String, GrammarInfoTree> cache = new HashMap<String, GrammarInfoTree>();
    
    public static GrammarInfoTree getGrammarInfoTree(String bot, String pattern, File aimlFile, File grammarFolder) {
        try {
            String key = bot + "-" + pattern;
            GrammarInfoTree git = cache.get(key);
            if (git == null) {
                //the logic is actually here
                int dot = pattern.indexOf(".");
                String namespace = pattern.substring(0, dot - 1);
                String term = pattern.substring(dot + 1);
                
                git = new GrammarInfoTree(namespace, term);
                GrammarInfoNode root = new GrammarInfoNode(namespace, term, "public", 0);
                git.root = lookup(grammarFolder, namespace, term, root);
                
                cache.put(key, git);
            }
            
            return git;
        } catch (Exception e) {
            Logger.warn(e.getMessage(), e);
            return null;
        }
    }
    
    private static GrammarInfoNode lookup(File file, String namespace, String term, GrammarInfoNode node) {
        return node;
    }
}
