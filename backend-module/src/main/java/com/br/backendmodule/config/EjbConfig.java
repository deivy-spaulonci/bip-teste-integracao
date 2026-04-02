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

        // 1. O nome JNDI completo do seu EJB no servidor
        factory.setJndiName("ejb:/ejb-module-1.0-SNAPSHOT/BeneficioEjbService!com.br.ejb.service.BeneficioEjbServiceRemote");

        // 2. Propriedades de conexão (Substitua pelos dados do seu servidor)
        Properties env = new Properties();
        // Para WildFly/JBoss moderno:
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        env.put(Context.PROVIDER_URL, "remote+http://localhost:8080");

        // Caso use JBoss antigo (AS 7/EAP 6):
        // env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        // env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        factory.setJndiEnvironment(env);
        factory.setProxyInterface(com.br.ejb.service.BeneficioEjbServiceRemote.class);
        factory.setLookupOnStartup(true);
        factory.setExposeAccessContext(true);

        return factory;
    }
}


