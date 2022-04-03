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

import org.inlambda.tasuka.profiler.Profilers;
import org.inlambda.tasuka.profiler.node.ProfilerNode;
import org.inlambda.tasuka.profiler.topic.ProfilerTopic;
import org.inlambda.tasuka.profiler.topic.TopicType;

import java.util.function.Consumer;

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

    public enum SampleTopic implements ProfilerTopic {
        HTTP_REQUEST(TopicType.COMBINED, "http Request"),
        TICK(TopicType.COLLECTIVE, "tick");
        private final TopicType topicType;
        private final String name;

        SampleTopic(TopicType topicType, String name) {
            this.topicType = topicType;
            this.name = name;
        }

        @Override
        public TopicType getType() {
            return topicType;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
