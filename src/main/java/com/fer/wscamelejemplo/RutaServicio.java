/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fer.wscamelejemplo;

import javax.ws.rs.core.MediaType;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.language.SimpleExpression;
import org.apache.camel.model.rest.RestBindingMode;

/**
 *
 * @author fercris
 */
public class RutaServicio extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        restConfiguration().component("undertow").
                contextPath("/api").bindingMode(RestBindingMode.auto)
                .port(8082)
                ;
        
        rest().path("/funcion").consumes(MediaType.APPLICATION_JSON).produces(MediaType.APPLICATION_JSON)
                .post().to("direct:salida");
        
        from("direct:salida").log("Salida ${body}").setBody(new SimpleExpression("{\"salida\":\"ok\"}"));
                
    }
    
}
