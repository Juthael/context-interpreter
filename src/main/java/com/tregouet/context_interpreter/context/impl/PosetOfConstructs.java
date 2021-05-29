package com.tregouet.context_interpreter.context.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.context.IPosetOfConstructs;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public class PosetOfConstructs implements IPosetOfConstructs {

	private final Map<IConstruct, ICategory> constructToCat;
	private final Map<IConstruct, Set<IConstruct>> relation = new HashMap<IConstruct, Set<IConstruct>>();
	private final Map<IConstruct, Set<IConstruct>> succRelation = new HashMap<IConstruct, Set<IConstruct>>();
	
	PosetOfConstructs(IPosetOfCategories catPoset){
		constructToCat = catPoset.getConstructToCategoryMap();
	}
	
	@Override
	public int compare(IConstruct construct1, IConstruct construct2) {
		// if (con)
	}

	@Override
	public Set<IConstruct> getPredecessors(IConstruct construct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IConstruct> getSuccessors(IConstruct construct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IConstruct> getLowerBounds(IConstruct construct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IConstruct> getUpperBounds(IConstruct construct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<List<IConstruct>> getSpanningChains() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<IConstruct, Set<IConstruct>> getRelation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<IConstruct, Set<IConstruct>> getSuccRelation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<IConstruct, Set<IConstruct>> getPrecRelation() {
		// TODO Auto-generated method stub
		return null;
	}

}
