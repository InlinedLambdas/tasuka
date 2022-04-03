package org.inlambda.tasuka.profiler.topic;

import org.jetbrains.annotations.ApiStatus;

/**
 * A `ProfilerTopic` represents a topic of profiling and determines how does your profiling data will be.
 *
 * It's suggested that users use it with an enum.
 */
@ApiStatus.AvailableSince("0.1.0")
public interface ProfilerTopic {
    TopicType getType();
    String getName();
}
