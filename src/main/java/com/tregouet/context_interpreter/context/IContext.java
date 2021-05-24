package com.tregouet.context_interpreter.context;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;

public interface IContext {
	
	Map<ICategory, Set<ICategory>> buildRelationOverCategories();
	
	TreeSet<IRepresentation> buildRepresentations();
	
	float getCtxtOptimalRepresentationCost();
	
	TreeSet<IRepresentation> getRepresentations();
	
	TreeSet<IRepresentation> getRepresentationsWithStructure(String structure);

}
