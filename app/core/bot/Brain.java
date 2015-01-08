/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Knowledge.java
 *
 */
package core.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

import core.bot.knowledge.*;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 7, 2015
 */
//A brain here is like a bot for program-ab
public class Brain
{
    public String name;
    public Properties properties;
    public PreProcessor preProcessor;
    public Graphmaster graph;
    
    public String bot_path;// = root_path+"/bots";
    public String aiml_path;// = bot_path+"/aiml";
    public String sets_path;// = bot_path+"/sets";
    public String maps_path;// = bot_path+"/maps";
    
    public HashMap<String, AIMLSet> setMap;
    public HashMap<String, AIMLMap> mapMap;
    
    
    public Brain(String name, String path) throws Exception {
        this.name = name;
        setPath(path);
        
        setMap = new HashMap<String, AIMLSet>();
        mapMap = new HashMap<String, AIMLMap>();
        
        //load everything
        this.graph = new Graphmaster(this);
        this.preProcessor = new PreProcessor(this);
        File propertiesFile = new File(new File(path), "properties.txt");
        addProperties(propertiesFile.getCanonicalPath());
        addCategoriesFromAIML(aiml_path);
    }
    
    private void setPath(String path) throws Exception {
        this.bot_path = path;
        this.aiml_path = (new File(new File(path), "confs")).getCanonicalPath();
        this.sets_path = (new File(new File(path), "sets")).getCanonicalPath();
        this.maps_path = (new File(new File(path), "maps")).getCanonicalPath();
    }
    
    private void addProperties(String path) throws Exception {
        properties.getProperties(path);
    }
    
    private int addCategoriesFromAIML(String path) throws Exception {
        int cnt = 0;
        String file;
        File folder = new File(path);
        if (folder.exists()) {
            File[] listOfFiles = IOUtils.listFiles(folder);
            for (File listOfFile :listOfFiles) {
                if (listOfFile.isFile()) {
                    file = listOfFile.getName();
                    if (file.toLowerCase().endsWith(".aiml")) {
                        ArrayList<Category> categories = AIMLProcessor.AIMLToCategories(path, file);
                        addMoreCategories(file, categories);
                        cnt += categories.size();
                    }
                }
            }
        }
        
        return cnt;
    }
    
    private void addMoreCategories(String file, ArrayList<Category> moreCategories) {
        for (Category c : moreCategories) {
            graph.addCategory(c);
        }
    }
}
