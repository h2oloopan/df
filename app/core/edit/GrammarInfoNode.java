/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * GrammarInfoNode.java
 *
 */
package core.edit;

import java.util.ArrayList;

import play.libs.Json;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Apr 7, 2015
 */
public class GrammarInfoNode
{
    public String namespace;
    public String term;
    public String type;
    public int line;
    public ArrayList<GrammarInfoNode> children;
    
    public GrammarInfoNode(String namespace, String term, String type, int line) {
        this.namespace = namespace;
        this.term = term;
        this.type = type;
        this.line = line;
        this.children = new ArrayList<GrammarInfoNode>();
    }
    
    @Override
    public String toString() {
        return Json.stringify(Json.toJson(this));
    }
}
