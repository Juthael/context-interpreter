package com.tregouet.context_interpreter.context;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public interface IPosetOfConstructs {
	
	public static final int UNCOMPARABLE = -2;
	public static final int INSTANTIATION_OF = - 1;
	public static final int SAME_AS = 0;
	public static final int ABSTRACTION_OF = 1;
	
	int compare(IConstruct construct1, IConstruct construct2);
	
	Set<IConstruct> getPredecessors(IConstruct construct);
	
	Set<IConstruct> getSuccessors(IConstruct construct);
	
	Set<IConstruct> getLowerBounds(IConstruct construct);
	
	Set<IConstruct> getUpperBounds(IConstruct construct);
	
	Set<List<IConstruct>> getSpanningChains();
	
	Map<IConstruct, Set<IConstruct>> getRelation();
	
	Map<IConstruct, Set<IConstruct>> getSuccRelation();
	
	Map<IConstruct, Set<IConstruct>> getPrecRelation();

}
