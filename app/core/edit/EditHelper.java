/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * EditHelper.java
 *
 */
package core.edit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.Logger;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Apr 7, 2015
 */
public class EditHelper
{
    private static HashMap<String, HashMap<String, String>> cache = new HashMap<String, HashMap<String, String>>();
    
    public static HashMap<String, String> getTerms(String folder) {
        try {
            HashMap<String, String> result = cache.get(folder);
            if (result != null) {
                return result;
            } else {
                result = new HashMap<String, String>();
                load(new File(folder), result);
                cache.put(folder, result);
                return result;
            }
            
        } catch (Exception e) {
            Logger.warn(e.getMessage(), e);
            return new HashMap<String, String>();
        }
    }
    
    public static void reloadTerms(String folder) {
        try {
            HashMap<String, String> result = new HashMap<String, String>();
            load(new File(folder), result);
            cache.put(folder, result);
        } catch (Exception e) {
            Logger.warn(e.getMessage(), e);
        }
    }
    
    private static void load(File file, HashMap<String, String> map) throws Exception {
        if (file.isDirectory()) {
            for (String name : file.list()) {
                File sub = new File(file, name);
                load(sub, map);
            }
        } else {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "GB18030"));
            String line;
            String namespace = "default";
            while ((line = br.readLine()) != null) {
              //try to find namespace
                String pattern = "\\s*namespace\\s+([^\\s]+)\\s*";
                Pattern np = Pattern.compile(pattern);
                Matcher nm = np.matcher(line);
                if (nm.matches()) {
                    namespace = nm.group(1).trim();
                }
                
                pattern = "\\s*public\\s+([^:]+):.+";
                boolean result = line.matches(pattern);
                if (result) {
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(line);
                    if (m.matches()) {
                        String publicTerm = m.group(1).trim();
                        String term = namespace + "." + publicTerm;
                        map.put(term, file.getCanonicalPath());
                    }
                }
            }
        }
    }
    
    
    /*
    public static GrammarInfoTree getGrammarInfoTree(String bot, String pattern, File aimlFile, File grammarFolder) {
        try {
            if (map == null) {
                load(map, grammarFolder);
            }
            
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
    
    private static void load(HashMap<String, String> map, File file) throws Exception {
        if (file.isDirectory()) {
            //directory
            for (String name : file.list()) {
                File sub = new File(file, name);
                load(map, sub);
            }
        } else {
            //file
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "GB18030"));
            String line;
            String namespace = "default";
            while ((line = br.readLine()) != null) {
              //try to find namespace
                String pattern = "\\s*namespace\\s+([^\\s]+)\\s*";
                Pattern np = Pattern.compile(pattern);
                Matcher nm = np.matcher(line);
                if (nm.matches()) {
                    namespace = nm.group(1).trim();
                }
                
                pattern = "\\s*public\\s+([^:]+):.+";
                boolean result = line.matches(pattern);
                if (result) {
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(line);
                    if (m.matches()) {
                        String publicTerm = m.group(1).trim();
                        String term = namespace + "." + publicTerm;
                        map.put(term, file.getCanonicalPath());
                    }
                }
            }
        }
    }
    
    private static GrammarInfoNode lookup(File file, String namespace, String term, GrammarInfoNode node) {
        if (node.type == "public") {
            String path = map.get(namespace + "." + term);
            if (path != null) {
                
            }
        }
        return node;
    }
    */
}
