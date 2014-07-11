package hu.dpc.sample;

import org.apache.camel.Body;

import javax.inject.Named;


@Named
public class HelloWorld {

    public String sayHello(@Body String message) {
        return ">> Hello " + message + " user.";
    }
}
