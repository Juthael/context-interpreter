package com.tregouet.context_interpreter.context.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.io.outputs.exceptions.VisualizationException;
import com.tregouet.context_interpreter.io.outputs.viz.IPosetOfCatGraphBuilder;
import com.tregouet.context_interpreter.io.outputs.viz.impl.PosetOfCatGraphBuilder;

public abstract class PosetOfCategories implements IPosetOfCategories {

	protected final List<IContextObject> objects;
	protected ICategory accept;
	protected ICategory preAccept;
	protected final Map<ICategory, Set<ICategory>> relation;
	protected final Map<ICategory, Set<ICategory>> succRelation = new HashMap<ICategory, Set<ICategory>>();
	protected final Map<ICategory, Set<ICategory>> precRelation = new HashMap<ICategory, Set<ICategory>>();
	
	public PosetOfCategories(List<IContextObject> objects) {
		relation = new HashMap<ICategory, Set<ICategory>>();
		this.objects = objects;
	}	
	
	protected PosetOfCategories(List<IContextObject> objects, Map<ICategory, Set<ICategory>> treeRelation) {
		this.objects = objects;
		relation = treeRelation;
	}	
	
	@Override
	public boolean buildSuccessorRelationGraph(String fileName) throws VisualizationException {
		IPosetOfCatGraphBuilder graphBuilder = new PosetOfCatGraphBuilder();
		try {
			graphBuilder.buildPosetOfCategoriesGraph(succRelation, fileName);
		} catch (VisualizationException e) {
			throw new VisualizationException("PosetOfCategories.buildSuccessorRelationGraph() : error"
					+ System.lineSeparator() + e.getMessage());
		}
		return true;
	}
	
	@Override
	public int compare(ICategory cat1, ICategory cat2) {
		if (relation.get(cat1).contains(cat2))
			return IPosetOfCategories.SUPER_CATEGORY;
		else if (relation.get(cat2).contains(cat1))
			return IPosetOfCategories.SUB_CATEGORY;
		else if (cat2.equals(cat1))
			return IPosetOfCategories.EQUALS;
		return IPosetOfCategories.UNCOMPARABLE;
	}
	
	@Override
	public ICategory getAcceptCategory() {
		return accept;
	}

	@Override
	public Set<ICategory> getCategories(){
		return relation.keySet();
	}
	
	@Override
	public Map<IConstruct, ICategory> getConstructToCategoryMap() {
		Map<IConstruct, ICategory> constructToCategory = new HashMap<IConstruct, ICategory>();
		for (ICategory category : relation.keySet()) {
			for (IConstruct construct : category.getIntent())
				constructToCategory.put(construct, category);
		}
		return constructToCategory;
	}
	
	@Override
	public Set<ICategory> getLowerSet(ICategory category) {
		return new HashSet<ICategory>(relation.get(category));
	}
	
	@Override
	public Set<ICategory> getObjectCategories() {
		Set<ICategory> objectCats = new HashSet<ICategory>();
		for (ICategory category : relation.keySet()) {
			if (category.type() == ICategory.LATT_OBJ)
				objectCats.add(category);
		}
		return objectCats;
	}

	@Override
	public List<IContextObject> getObjects() {
		return objects;
	}

	@Override
	public ICategory getPreAcceptCategory() {
		return preAccept;
	}

	@Override
	public Map<ICategory, Set<ICategory>> getPrecRelOverCategories() {
		return precRelation;
	}

	@Override
	public Set<ICategory> getPredecessors(ICategory category) {
		return new HashSet<>(precRelation.get(category));
	}

	@Override
	public Map<ICategory, Set<ICategory>> getRelOverCategories() {
		return relation;
	}

	@Override
	public Set<List<ICategory>> getSpanningChains() {
		return continueSpanningChains(getAcceptCategory());
	}

	@Override
	public Set<ICategory> getSuccessors(ICategory category) {
		return new HashSet<ICategory>(succRelation.get(category));
	}
	
	@Override
	public Map<ICategory, Set<ICategory>> getSuccRelOverCategories() {
		return succRelation;
	}

	@Override
	public Set<ICategory> getUpperSet(ICategory category) {
		Set<ICategory> upperBounds = new HashSet<ICategory>();
		for (ICategory cat : relation.keySet()) {
			if (relation.get(cat).contains(category))
			upperBounds.add(category);
		}
		return upperBounds;
	}

	//recursive
	private Set<List<ICategory>> continueSpanningChains(ICategory category){
		Set<List<ICategory>> chains = new HashSet<List<ICategory>>();
		if (!succRelation.get(category).isEmpty()) {
			Set<List<ICategory>> subchains = new HashSet<List<ICategory>>();
			for (ICategory succ : succRelation.get(category)) {
				//recursion
				subchains.addAll(continueSpanningChains(succ));
			}
			for (List<ICategory> subchain : subchains){
				List<ICategory> nextChain = new ArrayList<>(subchain);
				nextChain.add(category);
				chains.add(nextChain);
			}
		}
		else {
			List<ICategory> chain = new ArrayList<ICategory>();
			chain.add(category);
			chains.add(chain);
		}
		return chains;
	}

	//recursive
	protected void updateCategoryRanks(ICategory cat, int rank) {
		if (cat.rank() < rank || cat.type() == ICategory.LATT_MIN) {
			cat.setRank(rank);
			for (ICategory predecessor : precRelation.get(cat))
				updateCategoryRanks(predecessor, rank + 1);
		}
	}
	
	protected void buildSuccessorRelation() {
		for (ICategory cat : relation.keySet())
			succRelation.put(cat, new HashSet<ICategory>(relation.get(cat)));
		for (ICategory cat : succRelation.keySet()) {
			for (ICategory otherCat : succRelation.keySet()) {
				if (!cat.equals(otherCat) && succRelation.get(otherCat).contains(cat)) {
					succRelation.get(otherCat).removeAll(succRelation.get(cat));
				}
			}
		}
	}
	
	protected void buildPredecessorRelation() {
		for (ICategory cat : succRelation.keySet()) {
			precRelation.put(cat, new HashSet<ICategory>());
		}
		for (ICategory cat : succRelation.keySet()) {
			for (ICategory succCat : succRelation.get(cat))
				precRelation.get(succCat).add(cat);
		}
	}
	
	@Override
	public Set<ICategory> getUpperSet(Set<ICategory> categories) {
		Set<ICategory> upperBounds = new HashSet<ICategory>();
		ICategory[] catArray = categories.toArray(new ICategory[categories.size()]);
		for (int i = 0 ; i < categories.size() ; i++) {
			if (i == 0)
				upperBounds.addAll(getUpperSet(catArray[i]));
			else {
				upperBounds.retainAll(getUpperSet(catArray[i]));
			}
		}
		return upperBounds;
	}
	
	@Override
	public Set<ICategory> getLowerSet(Set<ICategory> categories) {
		Set<ICategory> lowerBounds = new HashSet<ICategory>();
		ICategory[] catArray = categories.toArray(new ICategory[categories.size()]);
		for (int i = 0 ; i < categories.size() ; i++) {
			if (i == 0)
				lowerBounds.addAll(getLowerSet(catArray[i]));
			else lowerBounds.retainAll(getLowerSet(catArray[i]));
		}
		return lowerBounds;
	}
	
}
