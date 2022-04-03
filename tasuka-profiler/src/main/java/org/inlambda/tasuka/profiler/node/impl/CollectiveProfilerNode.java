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

package org.inlambda.tasuka.profiler.node.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.inlambda.tasuka.profiler.node.NodeFactory;
import org.inlambda.tasuka.profiler.node.NodeIdentifier;
import org.inlambda.tasuka.profiler.node.ProfilerNode;
import org.inlambda.tasuka.profiler.topic.ProfilerTopic;
import org.inlambda.tasuka.profiler.util.NodeUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class CollectiveProfilerNode implements ProfilerNode, NodeIdentifier {
    private transient final NodeFactory nodeFactory;
    @Getter
    private final ProfilerTopic topic;
    @Getter
    private transient final ProfilerNode parent;
    @Getter
    private final String identifier;

    private final List<ProfilerNode> subNodes = new LinkedList<>();
    @Getter
    private transient final Instant startTime = Instant.now();
    private Duration duration = Duration.ZERO;
    @Getter
    private transient Instant endTime;

    @Override
    public ProfilerNode startOp(ProfilerTopic topic, String identifier) {
        return nodeFactory.create(topic, parent, identifier);
    }

    @Override
    public ProfilerNode end() {
        endTime = Instant.now();
        duration = Duration.between(startTime, endTime);
        if (parent != null) {
            parent.finalizeSubNode(this);
        }
        return parent;
    }

    @Override
    public void append(Duration duration) {
        this.duration = this.duration.plus(duration);
    }

    @Override
    public Duration getTime() {
        return duration;
    }

    @Override
    public void finalizeSubNode(ProfilerNode node) {
        var previous = subNodes.stream()
                .filter(e -> e.getTopic().equals(node.getTopic()) && e.getIdentifier().equals(node.getIdentifier()))
                .findFirst();
        // combine trees.
        previous.ifPresentOrElse(prev -> NodeUtil.combineTree(prev, node), () -> {
            subNodes.add(node);
        });
    }

    @Override
    public Collection<? extends ProfilerNode> getSubNodes() {
        return Collections.unmodifiableCollection(subNodes);
    }

    @Override
    public NodeIdentifier getNodeIdentifier() {
        return this;
    }

    @Override
    public boolean is(NodeIdentifier identifier) {
        return identifier == this;
    }
}
