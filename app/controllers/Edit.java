/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Edit.java
 *
 */
package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;

import core.ActorFarm;
import play.Logger;
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
    
    public Promise<Result> files() {
        try {
            final HashMap<String, String> list = new HashMap<String, String>();
            String folder = request().getQueryString("folder");
            if (folder == null) {
                throw new Exception("folder cannot be empty");
            }
            for (File entry : new File(folder).listFiles()) {
                if (entry.isFile()) {
                    list.put(entry.getName(), entry.getCanonicalPath());
                }
            }
            return Promise.promise(new Function0<Result>() {
                public Result apply() {
                    return ok(Json.toJson(list));
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
    
    public Promise<Result> folders() {
        try {
            final HashMap<String, String> list = new HashMap<String, String>();
            
            String bot = request().getQueryString("bot");
            String type = request().getQueryString("type");
            if (type == null) {
                throw new Exception("type cannot be empty");
            }
            String path = null;
            switch (type.toLowerCase()) {
                case "grammar":
                    path = farm.getGrammarPath(bot);
                    break;
                case "aiml":
                    path = farm.getAimlPath(bot);
                    break;
            }
            if (path != null) {
                list.put(".", path);
                for (File entry : new File(path).listFiles()) {
                    if (entry.isDirectory()) {
                        list.put(entry.getName(), entry.getCanonicalPath());
                    }
                }
            }
            
            return Promise.promise(new Function0<Result>() {
                public Result apply() {
                    return ok(Json.toJson(list));
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
    
    public Promise<Result> file() {
        try {
            final String path = request().getQueryString("path");
            final String encoding = request().getQueryString("encoding");
            return Promise.promise(new Function0<Result>() {
               public Result apply() {
                   try {
                       if (encoding != null) {
                           return ok(Json.toJson(farm.getFile(path, encoding)));
                       } else {
                           return ok(Json.toJson(farm.getFile(path)));
                       }
                   } catch (Exception e) {
                       Logger.error(e.getMessage(), e);
                       return badRequest(e.getMessage());
                   }
               }
            });
        } catch (final Exception e) {
            Logger.error(e.getMessage(), e);
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
    
    public Promise<Result> create() {
        try {
            JsonNode json = request().body().asJson();
            final String text = json.findPath("text").textValue();
            final String name = json.findPath("name").textValue();
            final String bot = json.findPath("bot").textValue();
            final String type = json.findPath("type").textValue();
            return Promise.promise(new Function0<Result>() {
                public Result apply() {
                    try {
                        return ok(farm.createFile(bot, name, type, text));
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
    
    public Promise<Result> remove() {
        try {
            final String path = request().getQueryString("path");
            return Promise.promise(new Function0<Result>() {
                public Result apply() {
                    try {
                        farm.removeFile(path);
                        return ok();
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
