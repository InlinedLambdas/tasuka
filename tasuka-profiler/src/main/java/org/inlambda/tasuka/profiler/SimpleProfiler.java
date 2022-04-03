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
import org.inlambda.tasuka.profiler.topic.ProfilerTopic;
import org.inlambda.tasuka.profiler.topic.TopicType;

import java.util.function.Consumer;

public class SimpleProfiler extends AbstractProfiler {
    private final Consumer<ProfilerNode> consumer;

    public SimpleProfiler(ProfilerNode root, Consumer<ProfilerNode> flushOp) {
        super(root);
        this.consumer = flushOp;
    }

    @Override
    public void flush(ProfilerNode node) {
        consumer.accept(node);
    }

    public static class SimpleTopic implements ProfilerTopic {

        @Override
        public TopicType getType() {
            return TopicType.COLLECTIVE;
        }

        @Override
        public String getName() {
            return "Service Startup";
        }
    }
}
