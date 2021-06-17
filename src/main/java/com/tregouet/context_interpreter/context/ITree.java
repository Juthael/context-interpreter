package com.tregouet.context_interpreter.context;

import java.util.Set;

public interface ITree<T> extends IUpperSemiLattice<T> {
	
	T getRoot();
	
	Set<T> getLeaves();

}
