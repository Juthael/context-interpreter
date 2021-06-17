package com.tregouet.context_interpreter.context.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.context.ITree;

public class Tree<T> extends UpperSemiLattice<T> implements ITree<T> {

	private final Set<T> leaves;
	
	public Tree(T seed) {
		super(seed);
		leaves = new HashSet<T>();
		leaves.add(seed);
	}
	
	//the specified subtrees must not have any common element
	public Tree(T root, Set<ITree<T>> subTrees) {
		super(root, subTrees);
		leaves = new HashSet<T>();
		for (ITree<T> subtree : subTrees) {
			leaves.addAll(subtree.getLeaves());
		}
	}
	
	//for test use only
	public Tree(Map<T, Set<T>> semiLattice) {
		super(semiLattice);
		leaves = getMinimalElements();
	}

	@Override
	public Set<T> getLeaves() {
		return leaves;
	}

}
