/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Edit.java
 *
 */
package controllers;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
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
            final String encoding = request().getQueryString("encoding");
            return Promise.promise(new Function0<Result>() {
               public Result apply() {
                   try {
                       if (encoding != null) {
                           return ok(farm.getFile(path, encoding));
                       } else {
                           return ok(farm.getFile(path));
                       }
                   } catch (Exception e) {
                       return badRequest(e.getMessage());
                   }
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
    
    public Promise<Result> upload() {
        try {
            JsonNode json = request().body().asJson();
            final String encoding = json.findPath("encoding").textValue();
            final String text = json.findPath("text").textValue();
            final String path = json.findPath("path").textValue();
            farm.updateFile(path, text, encoding);
            return Promise.promise(new Function0<Result>() {
                public Result apply() {
                    try {
                        if (encoding != null) {
                            farm.updateFile(path, text, encoding);
                            return ok();
                        } else {
                            farm.updateFile(path, text);
                            return ok();
                        }
                    } catch (Exception e) {
                        return badRequest(e.getMessage());
                    }
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
