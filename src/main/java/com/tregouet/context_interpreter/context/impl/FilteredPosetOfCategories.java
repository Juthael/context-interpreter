package com.tregouet.context_interpreter.context.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public class FilteredPosetOfCategories extends PosetOfCategories implements IPosetOfCategories {
	
	public FilteredPosetOfCategories(List<IContextObject> objects, Map<ICategory, Set<ICategory>> relation) {
		super(objects, relation);
		updateCategoryRanks();
	}
	
	@Override
	public ICategory getCatLatticeMin() {
		return null;
	}
	
	private void updateCategoryRanks() {
		for (ICategory objCat : latticeObj) {
			updateCategoryRanks(objCat, 1);
		}
	}

	//recursive
	private void updateCategoryRanks(ICategory cat, int rank) {
		if (cat.rank() < rank || cat.type() == ICategory.LATT_OBJ) {
			cat.setRank(rank);
			for (ICategory predecessor : precRelation.get(cat))
				updateCategoryRanks(predecessor, rank + 1);
		}
	}

}
