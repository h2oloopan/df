/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * User.java
 *
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import platform.models.User;
import play.libs.Json;
import play.libs.F.*;
import play.mvc.*;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Feb 26, 2015
 */
public class Users extends Controller
{
    public Promise<Result> readAll() {
        try {
            final List<User> users = User.find.all();
            return Promise.promise(new Function0<Result>() {
               public Result apply() {
                   return ok(Json.toJson(users));
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
