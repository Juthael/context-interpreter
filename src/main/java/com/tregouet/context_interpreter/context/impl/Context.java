package com.tregouet.context_interpreter.context.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.compiler_builder.ICompilerBuilder;
import com.tregouet.context_interpreter.context.IContext;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;


public class Context implements IContext {
	
	private final List<IContextObject> objects;
	private final IPosetOfCategories catPoset;
	private final ICompilerBuilder compilerBuilder;
	private final TreeSet<IRepresentation> representations;

	public Context(List<IContextObject> objects) {
		this.objects = objects;
		catPoset = new PosetOfCategories(objects);
		//yet to do
		compilerBuilder = null;
		representations = null;
	}

	public Map<ICategory, Set<ICategory>> buildRelationOverCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeSet<IRepresentation> buildRepresentations() {
		// TODO Auto-generated method stub
		return null;
	}

	public float getCtxtOptimalRepresentationCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	public TreeSet<IRepresentation> getRepresentations() {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeSet<IRepresentation> getRepresentationsWithStructure(String structure) {
		// TODO Auto-generated method stub
		return null;
	}

}
