package org.inlambda.tasuka.profiler;

import org.inlambda.tasuka.profiler.node.NodeIdentifier;
import org.inlambda.tasuka.profiler.topic.ProfilerTopic;
import org.jetbrains.annotations.ApiStatus;

/**
 * A Profiler represents a profiling session tree. It is used to record the time spent in codes.
 */
@ApiStatus.AvailableSince("0.1.0")
public interface Profiler {
    /**
     * Start a profiling session.
     */
    NodeIdentifier startOp(ProfilerTopic topic, String identifier);

    /**
     * End a profiling session.
     * @param identifier the identifier of the profiling session.
     */
    void endOp(NodeIdentifier identifier);

    /**
     * End current profiling session.
     *
     * If your surrounded codes contains some potential sub-profiler sessions, You'd be better to use {@link #endOp(NodeIdentifier)} or {@link #withOp(ProfilerTopic, String, Runnable)} to avoid possible undefined behaviours.
     */
    void endOp();

    /**
     * Run your runnable with a profiling session. This makes sure that the profiling session won't leak.
     * @param topic
     * @param identifier
     * @param runnable
     */
    default void withOp(ProfilerTopic topic, String identifier, Runnable runnable){
        var id = startOp(topic, identifier);
        runnable.run();
        endOp(id);
    }

}
