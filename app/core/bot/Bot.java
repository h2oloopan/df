/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Bot.java
 *
 */
package core.bot;

import core.grammar.GrammarMatcher;
import core.bot.ab.AIMLMap;
import core.bot.ab.AIMLSet;
import core.bot.ab.Category;
import core.bot.ab.ComprehensiveProcessor;
import core.bot.ab.Graphmaster;
import core.bot.ab.MagicBooleans;
import core.bot.ab.MagicNumbers;
import core.bot.ab.MagicStrings;
import core.bot.ab.Nodemapper;
import core.bot.ab.NodemapperOperator;
import core.bot.ab.PreProcessor;
import core.bot.ab.Properties;
import core.bot.ab.Timer;
import core.bot.ab.Utilities;
import core.bot.ab.handlers.TagHandlerCollection;
import core.bot.ab.utils.IOUtils;

import java.io.*;
import java.util.*;

import play.Logger;
import core.context.*;
import core.messages.SpecialText;
import core.storage.LogProvider;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 9, 2015
 */
public class Bot
{
    public final Properties properties = new Properties();
    public final PreProcessor preProcessor;
    public final Graphmaster brain;
    public Graphmaster learnfGraph;
    public Graphmaster learnGraph;
    
    public GrammarMatcher grammarMatcher;
    public LogProvider logProvider;

    // public Graphmaster unfinishedGraph;
    //  public final ArrayList<Category> categories;

    public String name=MagicStrings.default_bot_name;
    public HashMap<String, AIMLSet> setMap = new HashMap<String, AIMLSet>();
    public HashMap<String, AIMLMap> mapMap = new HashMap<String, AIMLMap>();
    public HashSet<String> pronounSet = new HashSet<String>();
    //public String root_path = "c:/ab";
    //public String bot_path = root_path+"/bots";
    //public String bot_name_path = bot_path+"/super";
    //public String aimlif_path = bot_path+"/aimlif";
    public String aiml_path, aimlif_path, config_path, sets_path, maps_path;
    
    
    
    /**
     * Set all directory path variables for this bot
     *
     * @param root        root directory of Program AB
     * @param name        name of bot
     */
    public void setAllPaths (String path, String name) throws Exception {
        //bot_path = root+"/bots";
        //bot_name_path = bot_path+"/"+name;
        //if (MagicBooleans.trace_mode) System.out.println("Name = "+name+" Path = "+bot_name_path);
        aiml_path = (new File(new File(path), "definition/aiml")).getCanonicalPath();
        aimlif_path = (new File(new File(path), "definition/aimlif")).getCanonicalPath();
        config_path = (new File(new File(path), "config")).getCanonicalPath();
        sets_path = (new File(new File(path), "sets")).getCanonicalPath();
        maps_path = (new File(new File(path), "maps")).getCanonicalPath();
    }

    /**
     * Constructor
     *
     * @param name     name of bot
     * @param path     bot path
     */
    
    public Bot(String name, String path, GrammarMatcher grammarMatcher, LogProvider logProvider) throws Exception {
        int cnt=0;
        int elementCnt=0;
        this.grammarMatcher = grammarMatcher;
        this.logProvider = logProvider;
        this.name = name;
        setAllPaths(path, name);
        this.brain = new Graphmaster(this);

        this.learnfGraph = new Graphmaster(this, "learnf");
        this.learnGraph = new Graphmaster(this, "learn");
  //      this.unfinishedGraph = new Graphmaster(this);
      //  this.categories = new ArrayList<Category>();

        
        preProcessor = new PreProcessor(this);
        addProperties();
        cnt = addAIMLSets();
        if (MagicBooleans.trace_mode) System.out.println("Loaded "+cnt+" set elements.");
        cnt = addAIMLMaps();
        if (MagicBooleans.trace_mode) System.out.println("Loaded "+cnt+" map elements");
        this.pronounSet = getPronouns();
        AIMLSet number = new AIMLSet(MagicStrings.natural_number_set_name, this);
        setMap.put(MagicStrings.natural_number_set_name, number);
        AIMLMap successor = new AIMLMap(MagicStrings.map_successor, this);
        mapMap.put(MagicStrings.map_successor, successor);
        AIMLMap predecessor = new AIMLMap(MagicStrings.map_predecessor, this);
        mapMap.put(MagicStrings.map_predecessor, predecessor);
        AIMLMap singular = new AIMLMap(MagicStrings.map_singular, this);
        mapMap.put(MagicStrings.map_singular, singular);
        AIMLMap plural = new AIMLMap(MagicStrings.map_plural, this);
        mapMap.put(MagicStrings.map_plural, plural);
        //System.out.println("setMap = "+setMap);
        Date aimlDate = new Date(new File(aiml_path).lastModified());
        Date aimlIFDate = new Date(new File(aimlif_path).lastModified());
        if (MagicBooleans.trace_mode) System.out.println("AIML modified "+aimlDate+" AIMLIF modified "+aimlIFDate);
        //readUnfinishedIFCategories();
        MagicStrings.pannous_api_key = Utilities.getPannousAPIKey(this);
        MagicStrings.pannous_login = Utilities.getPannousLogin(this);
        
        /*
        if (aimlDate.after(aimlIFDate)) {
            if (MagicBooleans.trace_mode) System.out.println("AIML modified after AIMLIF");
            cnt = addCategoriesFromAIML();
            writeAIMLIFFiles();
        }
        else {
            addCategoriesFromAIMLIF();
            if (brain.getCategories().size()==0) {
                System.out.println("No AIMLIF Files found.  Looking for AIML");
                cnt = addCategoriesFromAIML();
            }
        }
        */
        
        cnt = addCategoriesFromAIML();
        
        //Category b = new Category(0, "PROGRAM VERSION", "*", "*", MagicStrings.program_name_version, "update.aiml");
        //brain.addCategory(b);
        brain.nodeStats();
        learnfGraph.nodeStats();
    }
    
    /*
     * THE TALK INTERFACE
     */
    public String respond(String inputOriginal, String inputParsed, String that, String topic, Context context, Profile profile) throws Exception {
        String result = ComprehensiveProcessor.respond(this, context, profile, grammarMatcher, inputOriginal, inputParsed, that, topic);
        if (result == null) {
            return "";
        } else {
            return result.trim();
        }
        //TODO: repetition handling goes here?
        /*
        boolean repetition = true;
        //inputHistory.printHistory();
        for (int i = 0; i < MagicNumbers.repetition_count; i++) {
            //System.out.println(request.toUpperCase()+"=="+inputHistory.get(i)+"? "+request.toUpperCase().equals(inputHistory.get(i)));
            if (context.getLastQuery(i) == null || !input.toUpperCase().equals(context.getLastQuery(i).toUpperCase()))
                repetition = false;
        }
        if (input == null) repetition = false;
        if (repetition) {input = SpecialText.REPETITION; }
        return ComprehensiveProcessor.respond(this, context, profile, input, that, topic);
        */
    }
    
    
    
    HashSet<String> getPronouns() {
        HashSet<String> pronounSet = new HashSet<String>();
        String pronouns = Utilities.getFile(config_path+"/pronouns.txt");
        String[] splitPronouns = pronouns.split("\n");
        for (int i = 0; i < splitPronouns.length; i++) {
            String p = splitPronouns[i].trim();
            if (p.length() > 0) pronounSet.add(p);
        }
        if (MagicBooleans.trace_mode) System.out.println("Read pronouns: "+pronounSet);
        return pronounSet;
    }
    /**
     * add an array list of categories with a specific file name
     *
     * @param file      name of AIML file
     * @param moreCategories    list of categories
     */
    void addMoreCategories (String file, ArrayList<Category> moreCategories) {
        if (file.contains(MagicStrings.deleted_aiml_file)) {
           /* for (Category c : moreCategories) {
                //System.out.println("Delete "+c.getPattern());
                deletedGraph.addCategory(c);
            }*/

        } else if (file.contains(MagicStrings.learnf_aiml_file) ) {
            Logger.info("Reading Learnf file");
            for (Category c : moreCategories) {
                brain.addCategory(c);
                learnfGraph.addCategory(c);
                //patternGraph.addCategory(c);
            }
            //this.categories.addAll(moreCategories);
        } else {
            for (Category c : moreCategories) {
                //System.out.println("Brain size="+brain.root.size());
                //brain.printgraph();
                brain.addCategory(c);
                //patternGraph.addCategory(c);
                //brain.printgraph();
            }
            //this.categories.addAll(moreCategories);
        }
    }

    /**
     * Load all brain categories from AIML directory
     */
    int addCategoriesFromAIML() {
        Timer timer = new Timer();
        timer.start();
        int cnt=0;
        try {
            // Directory path here
            String file;
            File folder = new File(aiml_path);
            if (folder.exists()) {
                File[] listOfFiles = IOUtils.listFiles(folder);
                Logger.info("Loading AIML files from "+aiml_path);
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        file = listOfFile.getName();
                        if (file.endsWith(".aiml") || file.endsWith(".AIML")) {
                            if (MagicBooleans.trace_mode) System.out.println(file);
                            try {
                                ArrayList<Category> moreCategories = ComprehensiveProcessor.AIMLToCategories(aiml_path, file);
                                addMoreCategories(file, moreCategories);
                                cnt += moreCategories.size();
                            } catch (Exception iex) {
                                System.out.println("Problem loading " + file);
                                iex.printStackTrace();
                            }
                        }
                    }
                }
            }
            //else System.out.println("addCategoriesFromAIML: "+aiml_path+" does not exist.");
        } catch (Exception ex)  {
            Logger.error(ex.getMessage(), ex);
        }
        Logger.info("Loaded " + cnt + " categories in " + timer.elapsedTimeSecs() + " sec");
        return cnt;
    }

    /**
     * load all brain categories from AIMLIF directory
     */
    public int addCategoriesFromAIMLIF() {
        Timer timer = new Timer();
        timer.start();
        int cnt=0;
        try {
            // Directory path here
            String file;
            File folder = new File(aimlif_path);
            if (folder.exists()) {
                File[] listOfFiles = IOUtils.listFiles(folder);
                Logger.info("Loading AIML files from "+aimlif_path);
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        file = listOfFile.getName();
                        if (file.endsWith(MagicStrings.aimlif_file_suffix) || file.endsWith(MagicStrings.aimlif_file_suffix.toUpperCase())) {
                            Logger.info(file);
                            try {
                                ArrayList<Category> moreCategories = readIFCategories(aimlif_path + "/" + file);
                                cnt += moreCategories.size();
                                addMoreCategories(file, moreCategories);
                             //   MemStats.memStats();
                            } catch (Exception iex) {
                                System.out.println("Problem loading " + file);
                                iex.printStackTrace();
                            }
                        }
                    }
                }
            }
            else Logger.info("addCategoriesFromAIMLIF: "+aimlif_path+" does not exist.");
        } catch (Exception ex)  {
            Logger.error(ex.getMessage(), ex);
        }
        Logger.info("Loaded " + cnt + " categories in " + timer.elapsedTimeSecs() + " sec");
        return cnt;
    }


    /**
     * write all AIML and AIMLIF categories
     */
    public void writeQuit() {
        writeAIMLIFFiles();
        //System.out.println("Wrote AIMLIF Files");
        writeAIMLFiles();
        //System.out.println("Wrote AIML Files");
        /*  updateUnfinishedCategories();
        writeUnfinishedIFCategories();
*/
    }

    /**
     * read categories from specified AIMLIF file into specified graph
     *
     * @param graph   Graphmaster to store categories
     * @param fileName   file name of AIMLIF file
     */
    public int readCertainIFCategories(Graphmaster graph, String fileName) {
        int cnt=0;
        File file = new File(aimlif_path+"/"+fileName+MagicStrings.aimlif_file_suffix);
        if (file.exists()) {
            try {
                ArrayList<Category> certainCategories = readIFCategories(aimlif_path+"/"+fileName+MagicStrings.aimlif_file_suffix);
                for (Category d : certainCategories) graph.addCategory(d);
                cnt = certainCategories.size();
                System.out.println("readCertainIFCategories "+cnt+" categories from "+fileName+MagicStrings.aimlif_file_suffix);
            } catch (Exception iex) {
                System.out.println("Problem loading " + fileName);
                iex.printStackTrace();
            }
        }
        else System.out.println("No "+aimlif_path+"/"+fileName+MagicStrings.aimlif_file_suffix+" file found");
        return cnt;
    }

    /**
     * write certain specified categories as AIMLIF files
     *
     * @param graph       the Graphmaster containing the categories to write
     * @param file        the destination AIMLIF file
     */
    public void writeCertainIFCategories(Graphmaster graph, String file) {
        if (MagicBooleans.trace_mode) System.out.println("writeCertainIFCaegories "+file+" size= "+graph.getCategories().size());
        writeIFCategories(graph.getCategories(), file+MagicStrings.aimlif_file_suffix);
        File dir = new File(aimlif_path);
        dir.setLastModified(new Date().getTime());
    }

    /**
     * write deleted categories to AIMLIF file
     */


    /**
     * write learned categories to AIMLIF file
     */
    public void writeLearnfIFCategories() {
        writeCertainIFCategories(learnfGraph, MagicStrings.learnf_aiml_file);
    }

    /**
     * write unfinished categories to AIMLIF file
     */
   /* public void writeUnfinishedIFCategories() {
        writeCertainIFCategories(unfinishedGraph, MagicStrings.unfinished_aiml_file);
    }*/

    /**
     * write categories to AIMLIF file
     *
     * @param cats           array list of categories
     * @param filename       AIMLIF filename
     */
    public void writeIFCategories (ArrayList<Category> cats, String filename)  {
        //System.out.println("writeIFCategories "+filename);
        BufferedWriter bw = null;
        File existsPath = new File(aimlif_path);
        if (existsPath.exists())
        try {
            //Construct the bw object
            bw = new BufferedWriter(new FileWriter(aimlif_path+"/"+filename)) ;
            for (Category category : cats) {
                bw.write(Category.categoryToIF(category));
                bw.newLine();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the bw
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Write all AIMLIF files from bot brain
     */
    public void writeAIMLIFFiles () {
        if (MagicBooleans.trace_mode) System.out.println("writeAIMLIFFiles");
        HashMap<String, BufferedWriter> fileMap = new HashMap<String, BufferedWriter>();
        Category b = new Category(0, "BRAIN BUILD", "*", "*", "*", new Date().toString(), "update.aiml");
        brain.addCategory(b);
        ArrayList<Category> brainCategories = brain.getCategories();
        Collections.sort(brainCategories, Category.CATEGORY_NUMBER_COMPARATOR);
        for (Category c : brainCategories) {
            try {
                BufferedWriter bw;
                String fileName = c.getFilename();
                if (fileMap.containsKey(fileName)) bw = fileMap.get(fileName);
                else {
                    bw = new BufferedWriter(new FileWriter(aimlif_path+"/"+fileName+MagicStrings.aimlif_file_suffix));
                    fileMap.put(fileName, bw);

                }
                bw.write(Category.categoryToIF(c));
                bw.newLine();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Set set = fileMap.keySet();
        for (Object aSet : set) {
            BufferedWriter bw = fileMap.get(aSet);
            //Close the bw
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();

            }

        }
        File dir = new File(aimlif_path);
        dir.setLastModified(new Date().getTime());
    }

    /**
     * Write all AIML files.  Adds categories for BUILD and DEVELOPMENT ENVIRONMENT
     */
    public void writeAIMLFiles () {
        if (MagicBooleans.trace_mode) System.out.println("writeAIMLFiles");
        HashMap<String, BufferedWriter> fileMap = new HashMap<String, BufferedWriter>();
        Category b = new Category(0, "BRAIN BUILD", "*", "*", "*", new Date().toString(), "update.aiml");
        brain.addCategory(b);
        //b = new Category(0, "PROGRAM VERSION", "*", "*", MagicStrings.program_name_version, "update.aiml");
        //brain.addCategory(b);
        ArrayList<Category> brainCategories = brain.getCategories();
        Collections.sort(brainCategories, Category.CATEGORY_NUMBER_COMPARATOR);
        for (Category c : brainCategories) {

            if (!c.getFilename().equals(MagicStrings.null_aiml_file))
            try {
                //System.out.println("Writing "+c.getCategoryNumber()+" "+c.inputThatTopic());
                BufferedWriter bw;
                String fileName = c.getFilename();
                if (fileMap.containsKey(fileName)) bw = fileMap.get(fileName);
                else {
                    String copyright = Utilities.getCopyright(this, fileName);
                    bw = new BufferedWriter(new FileWriter(aiml_path+"/"+fileName));
                    fileMap.put(fileName, bw);
                    bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" +
                            "<aiml>\n");
                    bw.write(copyright);
                     //bw.newLine();
                }
                bw.write(Category.categoryToAIML(c)+"\n");
                //bw.newLine();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Set set = fileMap.keySet();
        for (Object aSet : set) {
            BufferedWriter bw = fileMap.get(aSet);
            //Close the bw
            try {
                if (bw != null) {
                    bw.write("</aiml>\n");
                    bw.flush();
                    bw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();

            }

        }
        File dir = new File(aiml_path);
        dir.setLastModified(new Date().getTime());
    }

    /**
     * load bot properties
     */
    void addProperties() {
        try {
            properties.getProperties(config_path+"/properties.txt");
        } catch (Exception ex)  {
            ex.printStackTrace();
        }
    }







    /**
     * read AIMLIF categories from a file into bot brain
     *
     * @param filename    name of AIMLIF file
     * @return   array list of categories read
     */
    public ArrayList<Category> readIFCategories (String filename) {
        ArrayList<Category> categories = new ArrayList<Category>();
        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(filename);
            // Get the object
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                try {
                    Category c = Category.IFToCategory(strLine);
                    categories.add(c);
                } catch (Exception ex) {
                    System.out.println("Invalid AIMLIF in "+filename+" line "+strLine);
                }
            }
            //Close the input stream
            br.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return categories;
    }





    /**
     * Load all AIML Sets
     */
    int addAIMLSets() {
        int cnt = 0;
        Timer timer = new Timer();
        timer.start();
        try {
            // Directory path here
            String file;
            File folder = new File(sets_path);
            if (folder.exists()) {
                File[] listOfFiles = IOUtils.listFiles(folder);
                if (MagicBooleans.trace_mode) System.out.println("Loading AIML Sets files from "+sets_path);
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        file = listOfFile.getName();
                        if (file.endsWith(".txt") || file.endsWith(".TXT")) {
                            if (MagicBooleans.trace_mode) System.out.println(file);
                            String setName = file.substring(0, file.length()-".txt".length());
                            if (MagicBooleans.trace_mode) System.out.println("Read AIML Set "+setName);
                            AIMLSet aimlSet = new AIMLSet(setName, this);
                            cnt += aimlSet.readAIMLSet(this);
                            setMap.put(setName, aimlSet);
                        }
                    }
                }
            }
            //else System.out.println("addAIMLSets: "+sets_path+" does not exist.");
        } catch (Exception ex)  {
            ex.printStackTrace();
        }
        return cnt;
    }

    /**
     * Load all AIML Maps
     */
    int addAIMLMaps() {
        int cnt=0;
        Timer timer = new Timer();
        timer.start();
        try {
            // Directory path here
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
            //else System.out.println("addAIMLMaps: "+maps_path+" does not exist.");
        } catch (Exception ex)  {
            ex.printStackTrace();
        }
        return cnt;
    }
    public void deleteLearnfCategories () {
        ArrayList<Category> learnfCategories = learnfGraph.getCategories();
        for (Category c : learnfCategories) {
            Nodemapper n = brain.findNode(c);
            System.out.println("Found node "+n+" for "+c.inputThatTopic());
            if (n != null) n.category = null;
        }
        learnfGraph = new Graphmaster(this);
    }
    public void deleteLearnCategories () {
        ArrayList<Category> learnCategories = learnGraph.getCategories();
        for (Category c : learnCategories) {
            Nodemapper n = brain.findNode(c);
            System.out.println("Found node "+n+" for "+c.inputThatTopic());
            if (n != null) n.category = null;
        }
        learnGraph = new Graphmaster(this);
    }

    /**
 * check Graphmaster for shadowed categories
 */
public void shadowChecker () {
    shadowChecker(brain.root) ;
}

    /** traverse graph and test all categories found in leaf nodes for shadows
     *
     * @param node
     */
    void shadowChecker(Nodemapper node) {
        if (NodemapperOperator.isLeaf(node)) {
            String input = node.category.getPattern();
            input = brain.replaceBotProperties(input);
            input = input.replace("*", "XXX").replace("_", "XXX").replace("^","").replace("#","");
            String grammar = node.category.getGrammar();
            String that = node.category.getThat().replace("*", "XXX").replace("_", "XXX").replace("^","").replace("#","");
            String topic = node.category.getTopic().replace("*", "XXX").replace("_", "XXX").replace("^","").replace("#","");
            input = instantiateSets(input);
            System.out.println("shadowChecker: input="+input);
            Nodemapper match = brain.match(input, grammar, that, topic);
            if (match != node) {
                System.out.println("MATCHED:     "+match.category.inputThatTopic());
                System.out.println("SHOULD MATCH:"+node.category.inputThatTopic());
            }
        }
        else {
            for (String key : NodemapperOperator.keySet(node)) {
                shadowChecker(NodemapperOperator.get(node, key));
            }
        }
    }

    public String instantiateSets(String pattern) {
        String[] splitPattern = pattern.split(" ");
        pattern = "";
        for (String x : splitPattern) {
          if (x.startsWith("<SET>")) {
              String setName = ComprehensiveProcessor.trimTag(x, "SET");
              AIMLSet set = setMap.get(setName);
              if (set != null) x = "FOUNDITEM";
              else x = "NOTFOUND";
          }
          pattern = pattern+" "+x;
        }
        return pattern.trim();
    }
}
