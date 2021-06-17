package com.tregouet.context_interpreter.context;

import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;

public interface ICategoryTree extends ICategoryUSL {
	
	ICategory getRoot();
	
	Set<ICategory> getLeaves();
	
	boolean superCatOf(ICategory aSuperCat, ICategory aSubCat);
	
	boolean genusOf(ICategory theLeastSuperCat, ICategory aSubCat);

}
