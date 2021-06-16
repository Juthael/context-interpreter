package com.tregouet.context_interpreter.context.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IRelation;
import com.tregouet.context_interpreter.context.ITreeOfCategories;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public class TreeOfCategories extends PosetOfCategories implements ITreeOfCategories {
	
	private final ICategory root;
	private final Set<ICategory> leaves = new HashSet<ICategory>();

	public TreeOfCategories(List<IContextObject> objects, IRelation<ICategory> treeRelation) {
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
	public ICategory getRoot() {
		return root;
	}
	
	@Override
	public ICategory getAcceptCategory() {
		return accept;
	}	
	
	@Override
	public ICategory getPreAcceptCategory() {
		return preAccept;
	}	

	@Override
	public Set<ICategory> getLeaves() {
		return leaves;
	}

	@Override
	public boolean superCatOf(ICategory aSuperCat, ICategory aSubCat) {
		return relation.get(aSuperCat).contains(aSubCat);
	}

	@Override
	public boolean genusOf(ICategory theLeastSuperCat, ICategory aSubCat) {
		return relation.getPredecessorsOf(theLeastSuperCat).contains(aSubCat);
	}

}
