package org.poc.api.impl.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.poc.api.impl.serviceimpl.ProductServiceImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ProductServiceImpl.class);
    }

}
