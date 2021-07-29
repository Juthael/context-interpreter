package com.tregouet.context_interpreter.context.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.ICategoryUSL;
import com.tregouet.context_interpreter.context.IPosetOfConstructs;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.root_to_leaves.data.IUpperSemiLattice;
import com.tregouet.root_to_leaves.data.impl.map.UpperSemiLattice;

public abstract class PosetOfConstructs implements IPosetOfConstructs {

	private final ICategoryUSL catPoset;
	private final Map<IConstruct, ICategory> constructToCat;
	private final IUpperSemiLattice<IConstruct> relation;
	
	public PosetOfConstructs(ICategoryUSL catPoset){
		this.catPoset = catPoset;
		constructToCat = catPoset.getConstructToCategoryMap();
		relation = setRelation();
	}
	
	@Override
	public int compare(IConstruct construct1, IConstruct construct2) {
		int catComparison = catPoset.compare(constructToCat.get(construct1), constructToCat.get(construct2));
		if (catComparison == IUpperSemiLattice.SUPER 
				&& IPosetOfConstructs.generates(construct1, construct2))
			return IPosetOfConstructs.ABSTRACTION_OF;
		else if (catComparison == IUpperSemiLattice.SUB && IPosetOfConstructs.generates(construct2, construct1))
			return IPosetOfConstructs.INSTANCE_OF;
		else if (catComparison == IUpperSemiLattice.EQUALS 
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
		return relation.getStrictLowerBounds(construct);
	}

	@Override
	public IConstruct getMaximum() {
		return relation.getMaximum();
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
		return relation.getMaxChainsFrom(getMaximum());
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
		return relation.getStrictUpperBounds(construct);
	}

	private IUpperSemiLattice<IConstruct> setRelation() {
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
		return new UpperSemiLattice<IConstruct>(relationMap);
	}

}
