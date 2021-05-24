package com.tregouet.context_interpreter.context;

import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;

public interface ICatRelationBldr {
	
	ICategory buildAcceptCategory();
	
	Map<ICategory, Set<ICategory>> buildCategoryLattice();
	
	ICategory buildPreAcceptCategory();
	
	Map<ICategory, Set<ICategory>> getRelOverCategories();
	
	Map<ICategory, Set<ICategory>> updateRelation(ICategory acceptCat, ICategory preAcceptCat);

}
