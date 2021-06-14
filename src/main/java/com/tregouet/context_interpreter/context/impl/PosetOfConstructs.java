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
import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public abstract class PosetOfConstructs implements IPosetOfConstructs {

	private final IPosetOfCategories catPoset;
	private final Map<IConstruct, ICategory> constructToCat;
	private final Map<IConstruct, Set<IConstruct>> relation = new HashMap<IConstruct, Set<IConstruct>>();
	private final Map<IConstruct, Set<IConstruct>> succRelation = new HashMap<IConstruct, Set<IConstruct>>();
	private final Map<IConstruct, Set<IConstruct>> precRelation = new HashMap<IConstruct, Set<IConstruct>>();
	private IConstruct maximum;
	private final Set<IConstruct> minima = new HashSet<IConstruct>();
	
	PosetOfConstructs(IPosetOfCategories catPoset){
		this.catPoset = catPoset;
		constructToCat = catPoset.getConstructToCategoryMap();
		setRelations();
		for (IConstruct construct : relation.keySet()) {
			int nbOfLowerBounds = relation.get(construct).size();
			if (nbOfLowerBounds == 0)
				minima.add(construct);
			else if (nbOfLowerBounds == relation.keySet().size() - 1)
				maximum = construct;
		}
	}
	
	@Override
	public int compare(IConstruct construct1, IConstruct construct2) {
		int catComparison = catPoset.compare(constructToCat.get(construct1), constructToCat.get(construct2));
		if (catComparison == IPosetOfCategories.SUPER 
				&& IPosetOfConstructs.generates(construct1, construct2))
			return IPosetOfConstructs.ABSTRACTION_OF;
		else if (catComparison == IPosetOfCategories.SUB && IPosetOfConstructs.generates(construct2, construct1))
			return IPosetOfConstructs.INSTANCE_OF;
		else if (catComparison == IPosetOfCategories.EQUALS 
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
		return maximum;
	}

	@Override
	public Set<IConstruct> getMinima() {
		return minima;
	}

	@Override
	public Map<IConstruct, Set<IConstruct>> getPrecRelation() {
		return precRelation;
	}

	@Override
	public Set<IConstruct> getPredecessors(IConstruct construct) {
		return precRelation.get(construct);
	}

	@Override
	public Map<IConstruct, Set<IConstruct>> getRelation() {
		return relation;
	}

	@Override
	public Set<List<IConstruct>> getSpanningChains() {
		return getChainsFrom(maximum);
	}
	
	@Override
	public Set<IConstruct> getSuccessors(IConstruct construct) {
		return succRelation.get(construct);
	}
	
	@Override
	public Map<IConstruct, Set<IConstruct>> getSuccRelation() {
		return succRelation;
	}

	@Override
	public Map<ICategory, Set<ICategory>> getTransitionRelationOverCategories() {
		Map<ICategory, Set<ICategory>> transitionRelation = new HashMap<ICategory, Set<ICategory>>();
		for (ICategory cat : catPoset.getAllCategoriesExceptLatticeMinimum()) {
			transitionRelation.put(cat, new HashSet<ICategory>());
		}
		for (IConstruct construct : relation.keySet()) {
			ICategory predecessorCat = constructToCat.get(construct);
			for (IConstruct succ : succRelation.get(construct)) {
				ICategory successorCat = constructToCat.get(succ);
				transitionRelation.get(predecessorCat).add(successorCat);
			}
		}
		return transitionRelation;
	}

	@Override
	public Set<IConstruct> getUpperBounds(IConstruct construct) {
		Set<IConstruct> upperBounds = new HashSet<IConstruct>();
		for (IConstruct currConst : relation.keySet()) {
			if (relation.get(currConst).contains(construct))
				upperBounds.add(currConst);
		}
		return upperBounds;
	}

	//recursive
	private Set<List<IConstruct>> getChainsFrom(IConstruct construct){
		Set<List<IConstruct>> chains = new HashSet<List<IConstruct>>();
		Set<IConstruct> successors = getSuccessors(construct);
		if (successors.isEmpty()) {
			List<IConstruct> chain = new ArrayList<IConstruct>();
			chain.add(construct);
			chains.add(chain);
		}
		else {
			for (IConstruct succ : successors) {
				for (List<IConstruct> chain : getChainsFrom(succ)) {
					List<IConstruct> newChain = new ArrayList<IConstruct>();
					newChain.add(construct);
					newChain.addAll(chain);
					chains.add(newChain);
				}
			}
		}
		return chains;
	}

	private void setRelations() {
		List<IConstruct> constructs = new ArrayList<IConstruct>();
		for (IConstruct construct : constructToCat.keySet()) {
			relation.put(construct, new HashSet<IConstruct>());
			constructs.add(construct);
		}
		for (int i = 0 ; i < relation.size() - 1 ; i++) {
			IConstruct c1 = constructs.get(i);
			for (int j = i + 1 ; j < relation.size() ; j++) {
				IConstruct c2 = constructs.get(j);
				int constructComp = compare(c1, c2);
				if (constructComp == IPosetOfConstructs.ABSTRACTION_OF)
					relation.get(c1).add(c2);
				else if (constructComp == IPosetOfConstructs.INSTANCE_OF)
					relation.get(c2).add(c1);
			}
		}
		for (IConstruct construct : relation.keySet()) {
			succRelation.put(construct, new HashSet<IConstruct>(relation.get(construct)));
		}
		for (int k = 0 ; k < constructs.size() ; k++) {
			for (int l = 0 ; l < constructs.size() ; l++) {
				if (k != l && succRelation.get(constructs.get(l)).contains(constructs.get(k))) {
					succRelation.get(constructs.get(l)).removeAll(relation.get(constructs.get(k)));
				}
			}
		}
		for (IConstruct construct : relation.keySet()) {
			precRelation.put(construct, new HashSet<IConstruct>());
		}
		for (IConstruct predecessor : succRelation.keySet()) {
			for (IConstruct successor : succRelation.get(predecessor)) {
				precRelation.get(successor).add(predecessor);
			}
		}
	}

}
