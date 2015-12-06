package com.blogspot.nombre_temp.jetty.jersey.multi.project.example;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import com.blogspot.nombre_temp.jetty.jersey.multi.project.example.util.ConfigurationProvider;

public class ExampleStarter {

    public static void main(String[] args) throws ConfigurationException {
        System.out.println("Starting!");

        ConfigurationProvider.startConfiguration();
        PropertiesConfiguration serverConfiguration = ConfigurationProvider.getServerConfiguration();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        contextHandler.setContextPath("/");

        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(serverConfiguration.getInt("server.max.queued.thread.pool"), 1);
        final Server jettyServer = new Server(queuedThreadPool);

        int acceptors = Runtime.getRuntime().availableProcessors();

        ServerConnector serverConnector = new ServerConnector(jettyServer, acceptors, -1);
        serverConnector.setPort(serverConfiguration.getInt("server.port"));
        serverConnector.setAcceptQueueSize(serverConfiguration.getInt("server.accept.queue.size"));

        jettyServer.addConnector(serverConnector);
        jettyServer.setHandler(contextHandler);

        ServletHolder jerseyServlet = contextHandler.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "com.blogspot.nombre_temp.jetty.jersey.multi.project.example.resource");

        try {
            jettyServer.start();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("Stopping!");

                        jettyServer.stop();
                        jettyServer.destroy();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            jettyServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
