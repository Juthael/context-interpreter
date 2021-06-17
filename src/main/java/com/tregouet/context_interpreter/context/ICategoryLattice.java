package com.tregouet.context_interpreter.context;

import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;

public interface ICategoryLattice extends ICategoryUSL {
	
	Set<ICategory> getAllCategoriesExceptLatticeMinimum();
	
	Map<ICategory, Set<ICategory>> getLatticeSuccRel();
	
	Set<ICategory> getLatticeAtoms();
	
	ICategory getLatticeMax();
	
	ICategory getLatticeMin();
	
	Set<ICategory> getLattice();
	
	Set<ICategory> getLatticeAbstCategories();
	
	IPOCLooselyRestricted getLooselyRestrictedPosetOfConstructs();
	
	Set<ICategoryTree> getTreesOfCategories();

}
