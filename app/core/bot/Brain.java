/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Knowledge.java
 *
 */
package core.bot;

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
    //public HashMap<String, AIMLSet> setMap;
    //public HashMap<String, AIMLMap> mapMap;
    
    //public String root_path;// = "c:/ab";
    public String bot_path;// = root_path+"/bots";
    //public String bot_name_path;// = bot_path+"/super";
    //public String aimlif_path;// = bot_path+"/aimlif";
    public String aiml_path;// = bot_path+"/aiml";
    //public String config_path;// = bot_path+"/config";
    //public String log_path;// = bot_path+"/log";
    public String sets_path;// = bot_path+"/sets";
    public String maps_path;// = bot_path+"/maps";
    
    public Brain(String name, String path) throws Exception {
        this.name = name;
        setPath(path);
        //load everything
        this.graph = new Graphmaster(this);
        this.preProcessor = new PreProcessor(this);
        File propertiesFile = new File(new File(path), "properties.txt");
        addProperties(propertiesFile.getCanonicalPath());
        File setsFolder = new File(new File(path), "sets");
        File mapsFolder = new File(new File(path), "maps");
        addAIMLSets(setsFolder.getCanonicalPath());
        addAIMLMaps(mapsFolder.getCanonicalPath());
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
    
    private int addAIMLSets(String path) throws Exception {
        int cnt = 0;
        // Directory path here
        String file;
        File folder = new File(sets_path);
        if (folder.exists()) {
            File[] listOfFiles = IOUtils.listFiles(folder);
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    file = listOfFile.getName();
                    if (file.endsWith(".txt") || file.endsWith(".TXT")) {
                        String setName = file.substring(0, file.length()-".txt".length());
                        AIMLSet aimlSet = new AIMLSet(setName, this);
                        cnt += aimlSet.readAIMLSet(this);
                        setMap.put(setName, aimlSet);
                    }
                }
            }
        }
        return cnt;
    }
    
    private int addAIMLMaps(String path) throws Exception {
        int cnt = 0;
        String file;
        File folder = new File(maps_path);
        if (folder.exists()) {
            File[] listOfFiles = IOUtils.listFiles(folder);
            if (MagicBooleans.trace_mode) System.out.println("Loading AIML Map files from "+maps_path);
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    file = listOfFile.getName();
                    if (file.endsWith(".txt") || file.endsWith(".TXT")) {
                        if (MagicBooleans.trace_mode) System.out.println(file);
                        String mapName = file.substring(0, file.length()-".txt".length());
                        if (MagicBooleans.trace_mode) System.out.println("Read AIML Map "+mapName);
                        AIMLMap aimlMap = new AIMLMap(mapName, this);
                        cnt += aimlMap.readAIMLMap(this);
                        mapMap.put(mapName, aimlMap);
                    }
                }
            }
        }
        return cnt;
    }
}
