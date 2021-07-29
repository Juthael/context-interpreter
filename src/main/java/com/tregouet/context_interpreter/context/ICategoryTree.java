package com.tregouet.context_interpreter.context;

import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;

public interface ICategoryTree extends ICategoryUSL {
	
	boolean genusOf(ICategory cat1, ICategory cat2);
	
	Set<ICategory> getLeaves();
	
	ICategory getRoot();
	
	boolean superCatOf(ICategory cat1, ICategory cat2);

}
