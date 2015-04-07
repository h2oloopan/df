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
    @Override
    public String toString() {
        return Json.stringify(Json.toJson(this));
    }
}
