/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Nodemapper.java
 *
 */
package core.bot.knowledge;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 7, 2015
 */
public class Nodemapper
{
    /*    public static int idCnt=0;
    public int id;*/
    public Category category = null;
    public int height = MagicNumbers.max_graph_height;
    public StarBindings starBindings = null;
    public HashMap<String, Nodemapper> map = null;
    public String key = null;
    public Nodemapper value = null;
    public boolean shortCut = false;
    public ArrayList<String> sets;
    /*    public Nodemapper () {
        id = idCnt++;
    }*/

}
