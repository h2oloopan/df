/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Api.java
 *
 */
package controllers;

import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import akka.actor.ActorRef;

import com.google.inject.Inject;

import core.ActorFarm;
import core.SessionTable;
import core.messages.CommandType;
import core.messages.Query;
import core.messages.Response;
import ca.rsvptech.qa.common.datatype.RsvpServiceResponse;
import ca.rsvptech.qa.common.datatype.RsvpServiceResponse.ResponseStatus;
import play.Logger;
import play.libs.Json;
import play.libs.F.*;
import play.mvc.*;

import static akka.pattern.Patterns.ask;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Mar 3, 2015
 */
public class Api extends Controller
{
    @Inject
    private ActorFarm farm;
    @Inject
    private SessionTable table;
    
    public Promise<Result> ask() {
        try {
            String bot = request().getQueryString("bot");
            String query = request().getQueryString("query");
            String uid = request().getQueryString("uid");
            String sid = request().getQueryString("sid");
            Logger.info("Asking bot " + bot + " for query " + query);
            if (uid == null) {
                uid = UUID.randomUUID().toString();
            }
            
            if (sid == null) {
                sid = table.getSid(uid);
            }
            
            Query q = new Query(CommandType.RESPOND, uid, sid, query);
            ActorRef actor = farm.getActor(bot);
            return Promise.wrap(ask(actor, q, 8000)).map(
                new Function<Object, Result>() {
                    public Result apply(Object message) {
                        Response response = (Response)message;
                        switch (response.getCode()) {
                        case 200:
                            return ok(Json.toJson(response));
                        case 500:
                            return internalServerError(response.getText());
                        default:
                            return ok(Json.toJson(response));
                        }
                    }
                }
            );
            
        } catch (final Exception e) {
            return Promise.promise(new Function0<Result>() {
               public Result apply() {
                    try
                    {
                        RsvpServiceResponse response = new RsvpServiceResponse();
                        response.setStatus(ResponseStatus.Error);
                        JAXBContext context = JAXBContext.newInstance(RsvpServiceResponse.class);
                        Marshaller marshaller;
                        marshaller = context.createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                        StringWriter writer = new StringWriter();
                        marshaller.marshal(response, writer);
                        return ok(writer.toString());
                    }
                    catch (Exception ex)
                    {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                        Logger.error(ex.getMessage(), ex);
                        return internalServerError(ex.getMessage());
                    }
               }
            });
        }
    }
    
    private String getXml(RsvpServiceResponse response) throws Exception {
        try
        {
            JAXBContext context = JAXBContext.newInstance(RsvpServiceResponse.class);
            Marshaller marshaller;
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(response, writer);
            return writer.toString();
        }
        catch (Exception ex)
        {
            // TODO Auto-generated catch block
            ex.printStackTrace();
            Logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
