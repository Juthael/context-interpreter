package com.tregouet.context_interpreter.context;

import java.util.Set;

public interface ITree<T> extends IRelation<T> {
	
	T getRoot();
	
	Set<T> getLeaves();

}
