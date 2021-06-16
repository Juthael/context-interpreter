package com.tregouet.context_interpreter.context.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.context_interpreter.context.ITree;

public class Tree<T> extends Relation<T> implements ITree<T> {

	private final T root;
	private final Set<T> leaves;
	
	public Tree(T seed) {
		super(seed);
		root = seed;
		leaves = new HashSet<T>();
		leaves.add(seed);
	}
	
	//the specified subtrees must not have any common element
	public Tree(T root, Set<ITree<T>> subTrees) {
		super(root, subTrees);
		this.root = root;
		leaves = new HashSet<T>();
		for (ITree<T> subtree : subTrees) {
			leaves.addAll(subtree.getLeaves());
		}
	}

	@Override
	public T getRoot() {
		return root;
	}

	@Override
	public Set<T> getLeaves() {
		return leaves;
	}

}
