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

package org.inlambda.tasuka.collector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.inlambda.tasuka.collector.serializer.ProfilerNodeSerializer;
import org.inlambda.tasuka.collector.serializer.TopicSerializer;
import org.inlambda.tasuka.profiler.node.ProfilerNode;
import org.inlambda.tasuka.profiler.topic.ProfilerTopic;

import java.util.function.Consumer;

public class GsonCollector implements Consumer<ProfilerNode> {
    private final Consumer<String> consumer;
    private final Gson serializer;

    public GsonCollector(Consumer<String> consumer) {
        this.consumer = consumer;
        serializer = new GsonBuilder()
                .registerTypeHierarchyAdapter(ProfilerTopic.class, new TopicSerializer())
                .registerTypeHierarchyAdapter(ProfilerNode.class, new ProfilerNodeSerializer())
                .create();
    }

    @Override
    public void accept(ProfilerNode profilerNode) {
        consumer.accept(serializer.toJson(profilerNode));
    }
}
