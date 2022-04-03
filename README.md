# Tasuka

Easy and simple profiler for all purposes.

# Example

```java
public class Sample {
    public static boolean shouldTick = true;

    public static void main(String[] args) {
        Consumer<ProfilerNode> resultProcessor = finalizedRootNode -> {
        };
        var profiler = Profilers.createSimpleProfiler(resultProcessor);
        var tickIdentifier = profiler.startOp(SampleTopic.TICK, "main tick");
        while (shouldTick) {
            profiler.withOp(SampleTopic.HTTP_REQUEST, null, () -> {
                // do some http Requests..
                profiler.withOp(SampleTopic.HTTP_REQUEST, null, () -> {
                    // do some http Requests, but results (used time) will be combined into parent.
                });
            });
        }
        profiler.endOp(tickIdentifier);
        profiler.endOp(); // this quits root node and deactivates profiler,
    }
}

```

[Full Code](./tasuka-profiler/src/test/java/Sample.java)