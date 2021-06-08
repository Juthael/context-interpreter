package com.tregouet.context_interpreter.io.outputs.viz;

import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.io.outputs.exceptions.VisualizationException;

public interface IPosetOfCatGraphBuilder {
	
	boolean buildPosetOfCategoriesGraph(Map<ICategory, Set<ICategory>> relationOverCats, String fileName) 
			throws VisualizationException;

}
