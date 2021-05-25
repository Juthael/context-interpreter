package com.tregouet.context_interpreter.context;

import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;

public interface ICatRelationBldr {
	
	ICategory getAcceptCategory();
	
	Set<ICategory> getCategories();
	
	Map<ICategory, Set<ICategory>> getCategoryLatticeSuccRel();
	
	ICategory getCatLatticeMax();
	
	ICategory getLatticeMin();
	
	Set<ICategory> getObjectCategories();
	
	ICategory getPreAcceptCategory();
	
	Map<ICategory, Set<ICategory>> getPrecRelOverCategories();
	
	Map<ICategory, Set<ICategory>> getRelOverCategories();
	
	Map<ICategory, Set<ICategory>> getSuccRelOverCategories();

}
