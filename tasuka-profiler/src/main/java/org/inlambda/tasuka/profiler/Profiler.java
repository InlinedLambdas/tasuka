/*
 * MIT License
 *
 * Copyright (c) 2022 InlinedLambda and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
