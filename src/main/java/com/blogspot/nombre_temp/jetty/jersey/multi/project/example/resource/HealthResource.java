package com.blogspot.nombre_temp.jetty.jersey.multi.project.example.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.blogspot.nombre_temp.jetty.jersey.multi.project.example.util.ConfigurationProvider;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @GET
    public String health() {
        PropertiesConfiguration appConfiguration = ConfigurationProvider.getApplicationConfiguration();
        String instanceName = appConfiguration.getString("app.instance.name");
        String instanceNumber = appConfiguration.getString("app.instance.number");

        return String.format("%s_%s: OK", instanceName, instanceNumber);
    }
}
