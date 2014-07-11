package hu.dpc.sample;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;


public class SimpleCamelRoute extends RouteBuilder {

    private String timerUri;

    public SimpleCamelRoute() {
        System.out.println(">> SimpleCamelRoute instantiated");
    }

    @Override
    public void configure() throws Exception {

        from(timerUri)
                // Set Body with text "Bean Injected"
                .setBody().simple("Bean Injected")
                // Lookup for bean injected by CDIcontainer
                .beanRef("helloWorld", "sayHello")
                        // Display response received in log when calling HelloWorld
                .log(">> Response : ${body}")
                .to("hmq:queue:inputqueue").setTrace("true");

    }

    public void setTimerUri(String timerUri) {
        this.timerUri = timerUri;
    }

}