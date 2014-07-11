package hu.dpc.sample;

import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.cdi.CdiCamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.component.properties.DefaultPropertiesResolver;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.properties.PropertiesResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;


@Singleton
@Startup
public class BootStrap {

    Logger logger = LoggerFactory.getLogger(BootStrap.class);

    @Inject
    CdiCamelContext camelCtx;

    @Inject
    SimpleCamelRoute simpleRoute;

    @Inject
    ConsumerCamelRoute consumerRoute;


    @Resource(lookup = "java:/ConnectionFactorxmly")
    ConnectionFactory connectionFactory;

    @PostConstruct
    public void init() {
        try {

            logger.info(">> Create CamelContext and register Camel Route.");

            // Define Timer URI
            simpleRoute.setTimerUri("timer://simple?fixedRate=true&period=10s");

            camelCtx.addComponent("hmq", JmsComponent.jmsComponent(connectionFactory));
            camelCtx.addRoutes(simpleRoute);
            camelCtx.addRoutes(consumerRoute);

            camelCtx.start();

            logger.info(">> CamelContext created and camel route started.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @PreDestroy
    public void stop() {
        camelCtx.stop();
    }


}
