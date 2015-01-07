/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * MagicBooleans.java
 *
 */
package core.bot.knowledge;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 7, 2015
 */
public class MagicBooleans
{
    public static boolean trace_mode = true;
    public static boolean enable_external_sets = true;
    public static boolean enable_external_maps = true;
    public static boolean jp_tokenize = false;
    public static boolean fix_excel_csv = true;
    public static boolean enable_network_connection = true;
    public static boolean cache_sraix = false;
    public static boolean qa_test_mode = false;
    public static boolean make_verbs_sets_maps = false;

    public static void trace(String traceString) {
        if (trace_mode) {
            System.out.println(traceString);
        }
    }
}
