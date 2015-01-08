/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Utilities.java
 *
 */
package core.bot.knowledge;

import java.io.*;
import java.util.*;

import core.bot.Brain;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 7, 2015
 */
public class Utilities
{
    /**
     * Excel sometimes adds mysterious formatting to CSV files.
     * This function tries to clean it up.
     *
     * @param line     line from AIMLIF file
     * @return   reformatted line
     */
    public static String fixCSV (String line) {
        while (line.endsWith(";")) line = line.substring(0, line.length()-1);
        if (line.startsWith("\"")) line = line.substring(1, line.length());
        if (line.endsWith("\"")) line = line.substring(0, line.length()-1);
        line = line.replaceAll("\"\"", "\"");
        return line;
    }
    public static String tagTrim(String xmlExpression, String tagName) {
        String stag = "<"+tagName+">";
        String etag = "</"+tagName+">";
        if (xmlExpression.length() >= (stag+etag).length()) {
            xmlExpression = xmlExpression.substring(stag.length());
            xmlExpression = xmlExpression.substring(0, xmlExpression.length()-etag.length());
        }
        return xmlExpression;
    }
    public static HashSet<String> stringSet(String... strings)  {
        HashSet<String> set = new HashSet<String>();
        for (String s : strings) set.add(s);
        return set;
    }
    public static String getFileFromInputStream(InputStream in)  {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
        String contents = "";
        try {
            while ((strLine = br.readLine()) != null)   {
                if (!strLine.startsWith(MagicStrings.text_comment_mark)) {
                if (strLine.length() == 0) contents += "\n";
                else contents  += strLine+"\n";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return contents.trim();
    }
    public static String getFile (String filename) {
        String contents = "";
        try {
            File file = new File(filename);
            if (file.exists()) {
                //System.out.println("Found file "+filename);
                FileInputStream fstream = new FileInputStream(filename);
                // Get the object
                contents = getFileFromInputStream(fstream) ;
                fstream.close();
            }
        } catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        //System.out.println("getFile: "+contents);
        return contents;
    }
    public static String getCopyrightFromInputStream(InputStream in)  {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
        String copyright = "";
        try {
            while ((strLine = br.readLine()) != null)   {
                if (strLine.length() == 0) copyright += "\n";
                else copyright += "<!-- "+strLine+" -->\n";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return copyright;
    }
    
    /**
     * Returns if a character is one of Chinese-Japanese-Korean characters.
     *
     * @param c
     *            the character to be tested
     * @return true if CJK, false otherwise
     */
    public static boolean isCharCJK(final char c) {
        if ((Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
                || (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
                || (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B)
                || (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS)
                || (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
                || (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT)
                || (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
                || (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS)) {
            return true;
        }
        return false;
    }
}
