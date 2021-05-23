package com.tregouet.context_interpreter.context.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.ICatRelationBldr;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.construct.impl.ContextObject;

public class CategoryFinder implements ICatRelationBldr {

	private final List<IContextObject> objects;
	private final Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents;
	private final Set<ICategory> latticeElmnt;
	private final ICategory accept;
	private final Set<ICategory> preAccept;
	private final ICategory latticeMax;
	private final Set<ICategory> latticeCat;
	private final Set<ICategory> latticeObj;
	private final ICategory latticeMin;
	private final Map<ICategory, Set<ICategory>> relOverCategories;
	
	public CategoryFinder(List<IContextObject> objects) {
		this.objects = objects;
		relOverCategories = buildCategoryLattice();
		//yetToDo
		intentsToExtents = null;
		latticeElmnt = null;
		accept = null;
		preAccept = null;;
		latticeMax = null;
		latticeCat = null;
		latticeObj = null;
		latticeMin = null;
	}

	public Map<ICategory, Set<ICategory>> getRelOverCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<ICategory, Set<ICategory>> buildCategoryLattice() {
		// TODO Auto-generated method stub
		return null;
	}

	public ICategory buildPreAcceptCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public ICategory buildAcceptCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<ICategory, Set<ICategory>> updateRelation(ICategory acceptCat, ICategory preAcceptCat) {
		// TODO Auto-generated method stub
		return null;
	}

}
