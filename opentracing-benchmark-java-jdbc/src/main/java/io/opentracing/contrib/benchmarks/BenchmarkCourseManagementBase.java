package io.opentracing.contrib.benchmarks;

import io.opentracing.contrib.benchmarks.course.CourseManagementApplication;
import io.opentracing.contrib.benchmarks.config.TracerImplementation;
import io.opentracing.contrib.benchmarks.course.model.entities.Course;
import io.opentracing.contrib.benchmarks.course.model.services.CourseService;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import java.util.List;

public class BenchmarkCourseManagementBase {

    public List<Course> getAllCourses(StateVariables state) {
        return state.service.findAll();
    }

    @State(Scope.Benchmark)
    public static class StateVariables {
        public CourseService service;
        public ConfigurableApplicationContext c;

        @TearDown(Level.Iteration)
        public void shutdownContext() {
            c.close();
        }

        public void initApplication() {
            c = SpringApplication.run(CourseManagementApplication.class);
            service = c.getBean(CourseService.class);
        }
    }

    public static class StateVariablesNotInstrumented extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, TracerImplementation.NO_INSTRUMENTATION);
            initApplication();
        }
    }

    public static class StateVariablesJaeger extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, TracerImplementation.JAEGERTRACER);
            initApplication();
        }
    }

    public static class StateVariablesNoopTracer extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, TracerImplementation.NOOPTRACER);
            initApplication();
        }
    }

    public static class StateVariablesHaystack extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, TracerImplementation.HAYSTACKTRACER);
            initApplication();
        }
    }

    public static class StateVariablesMockTracer extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, TracerImplementation.MOCKTRACER);
            initApplication();
        }
    }
}


