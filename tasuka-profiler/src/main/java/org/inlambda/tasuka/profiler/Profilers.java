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

import org.inlambda.tasuka.profiler.node.ProfilerNode;
import org.inlambda.tasuka.profiler.node.impl.SimpleNodeFactory;
import org.inlambda.tasuka.profiler.topic.ProfilerTopic;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

/**
 * Utility methods to create profilers, also used to get the default profiler.
 */
@ApiStatus.AvailableSince("0.1.0")
public class Profilers {
    private static Profiler defaultProfiler;

    public static void setDefaultProfiler(Profiler defaultProfiler) {
        Profilers.defaultProfiler = defaultProfiler;
    }

    public static Profiler getDefaultProfiler() {
        return defaultProfiler;
    }

    public static ProfilerNode createRootNode(ProfilerTopic rootTopic, String identifier) {
        var factory = new SimpleNodeFactory();
        return factory.create(rootTopic, null, identifier);
    }

    public static Profiler createSimpleProfiler(Consumer<ProfilerNode> finalizer) {
        return new SimpleProfiler(createRootNode(new SimpleProfiler.SimpleTopic(), null), finalizer);
    }

    public static Profiler createEmptyProfiler() {
        return new EmptyProfiler();
    }
}
