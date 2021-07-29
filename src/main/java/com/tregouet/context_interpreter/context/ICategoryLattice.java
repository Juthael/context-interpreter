package com.tregouet.context_interpreter.context;

import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;

public interface ICategoryLattice extends ICategoryUSL {
	
	Set<ICategory> getAllCategoriesExceptLatticeMinimum();
	
	Set<ICategory> getLattice();
	
	Set<ICategory> getLatticeAbstCategories();
	
	Set<ICategory> getLatticeAtoms();
	
	ICategory getLatticeMax();
	
	ICategory getLatticeMin();
	
	Map<ICategory, Set<ICategory>> getLatticeSuccRel();
	
	IPOCLooselyRestricted getLooselyRestrictedPosetOfConstructs();
	
	Set<ICategoryTree> getCategoryTrees();

}
