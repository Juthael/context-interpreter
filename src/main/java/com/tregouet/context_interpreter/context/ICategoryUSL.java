package com.tregouet.context_interpreter.context;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.io.outputs.exceptions.VisualizationException;
import com.tregouet.context_interpreter.io.outputs.viz.IPosetOfCatGraphBuilder;
import com.tregouet.context_interpreter.io.outputs.viz.impl.PosetOfCatGraphBuilder;

public interface ICategoryUSL {
	
	public static int compareStatic(ICategory cat1, ICategory cat2) {
		if (!cat1.getExtent().equals(cat2.getExtent())) {
			if (cat1.getExtent().containsAll(cat2.getExtent()))
				return IUpperSemiLattice.SUPER;
			if (cat2.getExtent().containsAll(cat1.getExtent()))
				return IUpperSemiLattice.SUB;
			return IUpperSemiLattice.UNCOMPARABLE;
		}
		else {
			if (cat1.type() > cat2.type())
				return IUpperSemiLattice.SUPER;
			return IUpperSemiLattice.SUB;
		}
	}
	
	public static boolean buildSpecifiedPosetGraph(String fileName, Map<ICategory, Set<ICategory>> posetOfCats) 
			throws VisualizationException {
		IPosetOfCatGraphBuilder graphBuilder = new PosetOfCatGraphBuilder();
		try {
			graphBuilder.buildPosetOfCategoriesGraph(posetOfCats, fileName);
		} catch (VisualizationException e) {
			throw new VisualizationException("PosetOfCategories.buildSuccessorRelationGraph() : error"
					+ System.lineSeparator() + e.getMessage());
		}
		return true;
	}	
	
	boolean buildSuccessorRelationGraph(String fileName) throws VisualizationException;
	
	int compare(ICategory cat1, ICategory cat2);
	
	ICategory getAcceptCategory();
	
	Set<ICategory> getCategories();
	
	Map<IConstruct, ICategory> getConstructToCategoryMap();	
	
	Set<ICategory> getLowerSet(ICategory category);
	
	Set<ICategory> getObjectCategories();
	
	List<IContextObject> getObjects();
	
	ICategory getPreAcceptCategory();
	
	Set<ICategory> getPredecessors(ICategory category);
	
	Set<List<ICategory>> getSpanningChains();
	
	Set<ICategory> getSuccessors(ICategory category);
	
	Set<ICategory> getUpperSet(ICategory category);
	
	Set<ICategory> getUpperSet(Set<ICategory> categories);
	
	Set<ICategory> getLowerSet(Set<ICategory> categories);

	Map<ICategory, Set<ICategory>> getPrecRelOverCategories();

	Map<ICategory, Set<ICategory>> getRelOverCategories();

	Map<ICategory, Set<ICategory>> getSuccRelOverCategories();

}
