package com.tregouet.context_interpreter.context.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.ICategoryTree;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.representation.ITree;

public class CategoryTree extends CategoryUSL implements ICategoryTree {
	
	private final ICategory root;
	private final Set<ICategory> leaves = new HashSet<ICategory>();

	public CategoryTree(List<IContextObject> objects, ITree<ICategory> treeRelation) {
		super(objects, treeRelation);
		ICategory root = null;
		for (ICategory category : relation.getSet()) {
			if (category.type() == ICategory.ACCEPT)
				root = category;
			else if (category.type() == ICategory.LATT_OBJ)
				leaves.add(category);
		}
		this.root = root;
	}

	@Override
	public boolean genusOf(ICategory theLeastSuperCat, ICategory aSubCat) {
		return relation.getPredecessorsOf(aSubCat).contains(theLeastSuperCat);
	}
	
	@Override
	public Set<ICategory> getLeaves() {
		return leaves;
	}

	@Override
	public ICategory getRoot() {
		return root;
	}

	@Override
	public boolean superCatOf(ICategory aSuperCat, ICategory aSubCat) {
		return relation.getStrictLowerBounds(aSuperCat).contains(aSubCat);
	}

}
