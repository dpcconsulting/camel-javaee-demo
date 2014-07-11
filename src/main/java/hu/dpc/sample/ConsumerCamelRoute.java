package hu.dpc.sample;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

/**
 * User: charlesmoulliard
 * Date: 16/02/12.
 */

public class ConsumerCamelRoute extends RouteBuilder {

    public ConsumerCamelRoute() {
        System.out.println(">> SimpleCamelRoute instantiated");
    }

    @Override
    public void configure() throws Exception {

        RouteDefinition definition = from("properties:hmq:queue:{{queuename}}")
                .log(">> Response : ${body}");
        definition.setId("consumer");


    }


}