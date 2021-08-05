package io.opentracing.contrib.benchmarks;

import io.opentracing.contrib.benchmarks.course.model.entities.Course;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import java.util.List;

public class BenchmarkCourseManagementSampleTime extends BenchmarkCourseManagementBase {

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public List<Course> noInstrumentation(StateVariablesNotInstrumented state) {
        return getAllCourses(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public List<Course> noopTracer(StateVariablesNoopTracer state) {
        return getAllCourses(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public List<Course> jaegerTracer(StateVariablesJaeger state) {
        return getAllCourses(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public List<Course> haystackTracer(StateVariablesHaystack state) {
        return getAllCourses(state);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public List<Course> mockTracer(StateVariablesMockTracer state) {
        return getAllCourses(state);
    }
}


