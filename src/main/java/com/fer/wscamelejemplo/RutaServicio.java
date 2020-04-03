/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fer.wscamelejemplo;

import com.fasterxml.jackson.core.JsonParseException;
import javax.ws.rs.core.MediaType;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.language.SimpleExpression;
import org.apache.camel.model.rest.RestBindingMode;

/**
 *
 * @author fercris
 */
public class RutaServicio extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        JacksonDataFormat format = new JacksonDataFormat(Persona.class);
        restConfiguration()
                .component("undertow").
                contextPath("/api").bindingMode(RestBindingMode.auto)
                .port(8082)
                .dataFormatProperty("prettyPrint", "true")
                /*
                    add swagger api-doc out of the box
                 */
                .apiContextPath("/api-docs")
                .apiProperty("api.title", "Ejercicio ejemplo")
                .apiProperty("api.version", "1.2.3")
                .apiProperty("base.path", "/api")
                .apiProperty("cors", "true");
        ;
        onException(JsonParseException.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                .setBody().constant("Invalid json data");
        rest()
                .description("1", "Gestión persona", "java")
                .path("/funcion")
                .consumes(MediaType.APPLICATION_JSON)
                .produces(MediaType.APPLICATION_JSON)
                .post()
                .description("1.1", "Grabar persona", "java")
                .to("activemq:cola:servicio") // .type(Persona.class)
                ;
        from("activemq:cola:servicio")
                
              //  .marshal(format)
                //  choice().when()
                .log("Salida ${body}") //.setBody(new SimpleExpression("{\"salida\":\"ok\"}"))
                ;
        //from("direct:salida").log("Salida ${body}").setBody(new SimpleExpression("{\"salida\":\"ok\"}"));

    }

}
