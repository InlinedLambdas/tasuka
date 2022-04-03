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

package org.inlambda.tasuka.profiler.node;

import org.inlambda.tasuka.profiler.topic.ProfilerTopic;
import org.jetbrains.annotations.ApiStatus;

import java.time.Duration;
import java.util.Collection;

/**
 * A ProfilerNode represents a node in the profiler tree, which records time and processes the result.
 * <p>
 * ProfilerNodes shouldn't be directly used. Instead, only use the {@link org.inlambda.tasuka.profiler.Profiler}.
 */
@ApiStatus.Internal
public interface ProfilerNode {
    /**
     * Starts a new session
     *
     * @return the session
     */
    ProfilerNode startOp(ProfilerTopic topic, String identifier);

    /**
     * Stops the profiler node.
     *
     * @return parent node or null.
     */
    ProfilerNode end();

    ProfilerTopic getTopic();

    String getIdentifier();

    /**
     * Add duration directly.
     *
     * @param duration duration to end
     */
    @ApiStatus.Internal
    void append(Duration duration);

    /**
     * Get the time stored in profiler node.
     * It won't return current session. Only use it when finalizing the root node.
     *
     * @return time
     */
    Duration getTime();

    /**
     * Collect data from the profiler node.
     *
     * @param node
     */
    void finalizeSubNode(ProfilerNode node);

    Collection<? extends ProfilerNode> getSubNodes();

    boolean is(NodeIdentifier identifier);

    NodeIdentifier getNodeIdentifier();

    ProfilerNode getParent();
}
