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

import lombok.RequiredArgsConstructor;
import org.inlambda.tasuka.profiler.node.NodeIdentifier;
import org.inlambda.tasuka.profiler.node.ProfilerNode;
import org.inlambda.tasuka.profiler.topic.ProfilerTopic;
import org.inlambda.tasuka.profiler.util.NodeUtil;

@RequiredArgsConstructor
public abstract class AbstractProfiler implements Profiler {
    private final ProfilerNode root;

    private ProfilerNode currentNode;
    private volatile boolean activated = true;

    @Override
    public NodeIdentifier startOp(ProfilerTopic topic, String identifier) {
        if (!activated) {
            return null;
        }
        currentNode = currentNode.startOp(topic, identifier);
        return currentNode.getNodeIdentifier();
    }

    @Override
    public void endOp(NodeIdentifier identifier) {
        if (!activated) {
            return;
        }
        // search
        var node = NodeUtil.searchByParents(currentNode, identifier);
        if (node == null) {
            throw new IllegalArgumentException("Node not found");
        }
        NodeUtil.finalizeTree(node);
        currentNode = node.getParent();
        if (currentNode == null) {
            activated = false;
            flush(root);
        }
    }


    @Override
    public void endOp() {
        if (!activated) {
            return;
        }
        if (currentNode == root) {
            currentNode.end();
            activated = false;
            flush(root);
            return;
        }
        currentNode = currentNode.end();
    }

    /**
     * Output the final data.
     *
     * @param node root node
     */
    protected abstract void flush(ProfilerNode node);

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
