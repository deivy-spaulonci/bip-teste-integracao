package com.br.backendmodule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.Context;
import java.util.Properties;

@Configuration
public class EjbConfig {

    @Bean
    public JndiObjectFactoryBean beneficioEjbServiceRemote() {
        JndiObjectFactoryBean factory = new JndiObjectFactoryBean();

        factory.setJndiName("ejb:/ejb-module-1.0-SNAPSHOT/BeneficioEjbService!com.br.ejb.service.BeneficioEjbServiceRemote");

        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        env.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
        factory.setJndiEnvironment(env);
        factory.setProxyInterface(com.br.ejb.service.BeneficioEjbServiceRemote.class);
        factory.setLookupOnStartup(true);
        factory.setExposeAccessContext(true);

        return factory;
    }
}


