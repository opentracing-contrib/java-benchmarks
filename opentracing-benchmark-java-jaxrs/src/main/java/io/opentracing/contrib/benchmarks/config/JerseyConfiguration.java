package io.opentracing.contrib.benchmarks.config;

import io.opentracing.contrib.benchmarks.course.resources.CourseResource;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/resources")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(CourseResource.class);
        register(LoggingFeature.class);
    }
}
