package org.inlambda.tasuka.profiler.topic;

import org.jetbrains.annotations.ApiStatus;

/**
 * Topic Types, defines the behaviour of your topic
 */
@ApiStatus.AvailableSince("0.1.0")
public enum TopicType {
    /**
     * Collective Topics will record their sub-nodes.
     */
    COLLECTIVE,
    /**
     * Combined Topics combines their sub-nodes into itself.
     */
    COMBINED
}
