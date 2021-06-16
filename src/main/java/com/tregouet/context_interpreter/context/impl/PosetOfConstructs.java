package com.tregouet.context_interpreter.context.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.context.IPosetOfConstructs;
import com.tregouet.context_interpreter.context.IRelation;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public abstract class PosetOfConstructs implements IPosetOfConstructs {

	private final IPosetOfCategories catPoset;
	private final Map<IConstruct, ICategory> constructToCat;
	private final IRelation<IConstruct> relation;
	
	public PosetOfConstructs(IPosetOfCategories catPoset){
		this.catPoset = catPoset;
		constructToCat = catPoset.getConstructToCategoryMap();
		relation = setRelation();
	}
	
	@Override
	public int compare(IConstruct construct1, IConstruct construct2) {
		int catComparison = catPoset.compare(constructToCat.get(construct1), constructToCat.get(construct2));
		if (catComparison == IRelation.SUPER 
				&& IPosetOfConstructs.generates(construct1, construct2))
			return IPosetOfConstructs.ABSTRACTION_OF;
		else if (catComparison == IRelation.SUB && IPosetOfConstructs.generates(construct2, construct1))
			return IPosetOfConstructs.INSTANCE_OF;
		else if (catComparison == IRelation.EQUALS 
				&& IPosetOfConstructs.generates(construct1, construct2) && IPosetOfConstructs.generates(construct2, construct1))
			return IPosetOfConstructs.SAME_AS;
		return IPosetOfConstructs.UNCOMPARABLE;
	}

	@Override
	public ICategory getCategoryOf(IConstruct construct) {
		return constructToCat.get(construct);
	}

	@Override
	public Set<IConstruct> getLowerBounds(IConstruct construct) {
		return relation.get(construct);
	}

	@Override
	public IConstruct getMaximum() {
		List<IConstruct> maxima = new ArrayList<IConstruct>(relation.getMaximalElements());
		if (maxima.size() == 1)
			return maxima.get(0);
		else return null;
	}

	@Override
	public Set<IConstruct> getMinima() {
		return relation.getMinimalElements();
	}

	@Override
	public Map<IConstruct, Set<IConstruct>> getPrecRelation() {
		return relation.getPrecRelationMap();
	}

	@Override
	public Set<IConstruct> getPredecessors(IConstruct construct) {
		return relation.getPredecessorsOf(construct);
	}

	@Override
	public Map<IConstruct, Set<IConstruct>> getRelation() {
		return relation.getRelationMap();
	}

	@Override
	public Set<List<IConstruct>> getSpanningChains() {
		return relation.getSpanningChainsFrom(getMaximum());
	}
	
	@Override
	public Set<IConstruct> getSuccessors(IConstruct construct) {
		return relation.getSuccessorsOf(construct);
	}
	
	@Override
	public Map<IConstruct, Set<IConstruct>> getSuccRelation() {
		return relation.getSuccRelationMap();
	}

	@Override
	public Set<IConstruct> getUpperSet(IConstruct construct) {
		return relation.getUpperSet(construct);
	}

	private IRelation<IConstruct> setRelation() {
		Map<IConstruct, Set<IConstruct>> relationMap = new HashMap<IConstruct, Set<IConstruct>>();
		List<IConstruct> constructs = new ArrayList<IConstruct>();
		for (IConstruct construct : constructToCat.keySet()) {
			relationMap.put(construct, new HashSet<IConstruct>());
			constructs.add(construct);
		}
		for (int i = 0 ; i < relationMap.size() - 1 ; i++) {
			IConstruct c1 = constructs.get(i);
			for (int j = i + 1 ; j < relationMap.size() ; j++) {
				IConstruct c2 = constructs.get(j);
				int constructComp = compare(c1, c2);
				if (constructComp == IPosetOfConstructs.ABSTRACTION_OF)
					relationMap.get(c1).add(c2);
				else if (constructComp == IPosetOfConstructs.INSTANCE_OF)
					relationMap.get(c2).add(c1);
			}
		}
		return new Relation<IConstruct>(relationMap);
	}

}
