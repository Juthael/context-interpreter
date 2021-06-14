package com.tregouet.context_interpreter.context.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.context.IRelation;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.io.outputs.exceptions.VisualizationException;
import com.tregouet.context_interpreter.io.outputs.viz.IPosetOfCatGraphBuilder;
import com.tregouet.context_interpreter.io.outputs.viz.impl.PosetOfCatGraphBuilder;

public abstract class PosetOfCategories implements IPosetOfCategories {

	protected final List<IContextObject> objects;
	protected IRelation<ICategory> relation;
	protected ICategory accept;
	protected ICategory preAccept;
	
	public PosetOfCategories(List<IContextObject> objects) {
		this.objects = objects;
	}	
	
	protected PosetOfCategories(List<IContextObject> objects, IRelation<ICategory> treeRelation) {
		this.objects = objects;
		relation = treeRelation;
	}	
	
	@Override
	public boolean buildSuccessorRelationGraph(String fileName) throws VisualizationException {
		IPosetOfCatGraphBuilder graphBuilder = new PosetOfCatGraphBuilder();
		try {
			graphBuilder.buildPosetOfCategoriesGraph(relation.getSuccRelationMap(), fileName);
		} catch (VisualizationException e) {
			throw new VisualizationException("PosetOfCategories.buildSuccessorRelationGraph() : error"
					+ System.lineSeparator() + e.getMessage());
		}
		return true;
	}
	
	@Override
	public int compare(ICategory cat1, ICategory cat2) {
		if (relation.get(cat1).contains(cat2))
			return IRelation.SUPER;
		else if (relation.get(cat2).contains(cat1))
			return IRelation.SUB;
		else if (cat2.equals(cat1))
			return IRelation.EQUALS;
		return IRelation.UNCOMPARABLE;
	}

	@Override
	public Set<ICategory> getCategories(){
		return relation.getSet();
	}
	
	@Override
	public Map<IConstruct, ICategory> getConstructToCategoryMap() {
		Map<IConstruct, ICategory> constructToCategory = new HashMap<IConstruct, ICategory>();
		for (ICategory category : relation.getSet()) {
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
		Set<ICategory> objectCats = relation.getSet().stream()
										.filter(n -> n.type() == ICategory.LATT_OBJ)
										.collect(Collectors.toSet());
		return objectCats;
	}

	@Override
	public List<IContextObject> getObjects() {
		return objects;
	}

	@Override
	public Map<ICategory, Set<ICategory>> getPrecRelOverCategories() {
		return relation.getPrecRelationMap();
	}

	@Override
	public Set<ICategory> getPredecessors(ICategory category) {
		return relation.getPredecessorsOf(category);
	}

	@Override
	public Map<ICategory, Set<ICategory>> getRelOverCategories() {
		return relation.getRelationMap();
	}

	@Override
	public Set<List<ICategory>> getSpanningChains() {
		return relation.getSpanningChainsFrom(getAcceptCategory());
	}

	@Override
	public Set<ICategory> getSuccessors(ICategory category) {
		return relation.getSuccessorsOf(category);
	}
	
	@Override
	public Map<ICategory, Set<ICategory>> getSuccRelOverCategories() {
		return relation.getSuccRelationMap();
	}

	@Override
	public Set<ICategory> getUpperSet(ICategory category) {
		return relation.getUpperSet(category);
	}

	//recursive
	protected void updateCategoryRanks(ICategory cat, int rank) {
		if (cat.rank() < rank || cat.type() == ICategory.LATT_MIN) {
			cat.setRank(rank);
			for (ICategory predecessor : relation.getPredecessorsOf(cat))
				updateCategoryRanks(predecessor, rank + 1);
		}
	}
	
	@Override
	public Set<ICategory> getUpperSet(Set<ICategory> categories) {
		return relation.getUpperSet(categories);
	}
	
	@Override
	public Set<ICategory> getLowerSet(Set<ICategory> categories) {
		return relation.getLowerSet(categories);
	}
	
}
