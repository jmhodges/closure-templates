/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.template.soy.exprtree;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Dummy node that serves as the root of an expression tree so that the tree can be arbitrarily
 * changed without needing to change the reference to the tree.
 *
 * <p> Important: Do not use outside of Soy code (treat as superpackage-private).
 *
 * <p> This node should always have exactly one child.
 *
 */
public final class ExprRootNode extends AbstractParentExprNode {
  public static List<ExprNode> unwrap(Iterable<ExprRootNode> exprs) {
    ImmutableList.Builder<ExprNode> builder = ImmutableList.builder();
    for (ExprRootNode expr : exprs) {
      builder.add(expr.getChild(0));
    }
    return builder.build();
  }

  public static List<ExprRootNode> wrap(Iterable<? extends ExprNode> exprs) {
    ImmutableList.Builder<ExprRootNode> builder = ImmutableList.builder();
    for (ExprNode expr : exprs) {
      builder.add(new ExprRootNode(expr));
    }
    return builder.build();
  }

  /**
   * Creates a new instance with the given node as the child.
   * @param child The child to add to the new node.
   */
  public ExprRootNode(ExprNode child) {
    super(child.getSourceLocation());
    this.addChild(child);
  }


  /**
   * Copy constructor.
   * @param orig The node to copy.
   */
  private ExprRootNode(ExprRootNode orig) {
    super(orig);
  }


  @Override public Kind getKind() {
    return Kind.EXPR_ROOT_NODE;
  }


  @Override public ExprNode getChild(int index) {
    Preconditions.checkArgument(index == 0);
    return super.getChild(0);
  }


  @Override public String toSourceString() {
    return getChild(0).toSourceString();
  }


  @Override public ExprRootNode clone() {
    return new ExprRootNode(this);
  }

}
