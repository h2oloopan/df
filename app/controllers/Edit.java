/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Edit.java
 *
 */
package controllers;

import java.util.ArrayList;

import com.google.inject.Inject;

import core.ActorFarm;
import play.libs.Json;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Feb 3, 2015
 */
public class Edit extends Controller
{
    @Inject
    private ActorFarm farm;
    
    
    public Promise<Result> file() {
        try {
            final String path = request().getQueryString("path");
            return Promise.promise(new Function0<Result>() {
               public Result apply() {
                   return ok(farm.getFile(path));
               }
            });
        } catch (final Exception e) {
            return Promise.promise(new Function0<Result>() {
                public Result apply() {
                    return badRequest(e.getMessage());
                }
            });
        }
    }
}
