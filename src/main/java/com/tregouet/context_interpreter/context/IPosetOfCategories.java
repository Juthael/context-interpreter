package com.tregouet.context_interpreter.context;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public interface IPosetOfCategories {
	
	public static final int UNCOMPARABLE = -2;
	public static final int SUB_CATEGORY = -1;
	public static final int EQUALS = 0;
	public static final int SUPER_CATEGORY = 1;
	
	public static int compareStatic(ICategory cat1, ICategory cat2) {
		if (!cat1.getExtent().equals(cat2.getExtent())) {
			if (cat1.getExtent().containsAll(cat2.getExtent()))
				return IPosetOfCategories.SUPER_CATEGORY;
			if (cat2.getExtent().containsAll(cat1.getExtent()))
				return IPosetOfCategories.SUB_CATEGORY;
			return IPosetOfCategories.UNCOMPARABLE;
		}
		else {
			if (cat1.type() > cat2.type())
				return SUPER_CATEGORY;
			return SUB_CATEGORY;
		}
	}
	
	int compare(ICategory cat1, ICategory cat2);
	
	ICategory getAcceptCategory();
	
	Set<ICategory> getCategories();
	
	Map<ICategory, Set<ICategory>> getCategoryLatticeSuccRel();
	
	ICategory getCatLatticeMax();
	
	Map<IConstruct, ICategory> getConstructToCategoryMap();
	
	Set<ICategory> getLattice();
	
	Set<ICategory> getLatticeAbstCategories();
	
	ICategory getCatLatticeMin();
	
	Set<ICategory> getLowerBounds(ICategory category);
	
	Set<ICategory> getObjectCategories();
	
	List<IContextObject> getObjects();
	
	ICategory getPreAcceptCategory();
	
	Map<ICategory, Set<ICategory>> getPrecRelOverCategories();
	
	Set<ICategory> getPredecessors(ICategory category);
	
	Map<ICategory, Set<ICategory>> getRelOverCategories();
	
	Set<List<ICategory>> getSpanningChains();
	
	Set<ICategory> getSuccessors(ICategory category);
	
	Map<ICategory, Set<ICategory>> getSuccRelOverCategories();
	
	Set<ICategory> getUpperBounds(ICategory category);

}
