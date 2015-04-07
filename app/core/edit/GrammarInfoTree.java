/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * GrammarTree.java
 *
 */
package core.edit;

import play.libs.Json;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Apr 7, 2015
 */
public class GrammarInfoTree
{
    public String namespace;
    public String term;
    public GrammarInfoNode root;
    
    public GrammarInfoTree(String namespace, String term) {
        this.namespace = namespace;
        this.term = term;
    }
    
    @Override
    public String toString() {
        return Json.stringify(Json.toJson(this));
    }
}
