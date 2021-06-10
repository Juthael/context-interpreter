package com.tregouet.context_interpreter.context;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.ISymbol;

public interface IPosetOfConstructs {
	
	public static final int UNCOMPARABLE = -2;
	public static final int INSTANCE_OF = - 1;
	public static final int SAME_AS = 0;
	public static final int ABSTRACTION_OF = 1;
	
	public static boolean generates(IConstruct construct1, IConstruct construct2) {
		int c1NbOfTerminals = construct1.getNbOfTerminals();
		int c2NbOfTerminals = construct2.getNbOfTerminals();
		if (c1NbOfTerminals <= c2NbOfTerminals) {
			List<ISymbol> c1List = construct1.getListOfSymbols();
			List<ISymbol> c2List = construct2.getListOfSymbols();
			int c1Idx = 0 + ((c1List.get(0) instanceof AVariable) ? 1 : 0);
			int c2Idx;
			for (c2Idx = 0 ; c1Idx < c1List.size() && c2Idx < c2List.size() ; c2Idx++) {
				if (c2List.get(c2Idx).equals(c1List.get(c1Idx))) {
					c1Idx++;
					if (c1Idx < c1List.size() && c1List.get(c1Idx) instanceof AVariable){
						//there cannot be 2 consecutive variables
						c1Idx++;
					}
				}
			}
			if (c1Idx == c1List.size() && 
					((c1List.get(c1Idx - 1) instanceof AVariable) || c2Idx == c2List.size())) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	int compare(IConstruct construct1, IConstruct construct2);
	
	ICategory getCategoryOf(IConstruct construct);
	
	Set<IConstruct> getLowerBounds(IConstruct construct);
	
	IConstruct getMaximum();
	
	Set<IConstruct> getMinima();
	
	Map<IConstruct, Set<IConstruct>> getPrecRelation();
	
	Set<IConstruct> getPredecessors(IConstruct construct);
	
	Map<IConstruct, Set<IConstruct>> getRelation();
	
	Set<List<IConstruct>> getSpanningChains();
	
	Set<IConstruct> getSuccessors(IConstruct construct);
	
	Map<IConstruct, Set<IConstruct>> getSuccRelation();
	
	Map<ICategory, Set<ICategory>> getTransitionRelationOverCategories();
	
	Set<IConstruct> getUpperBounds(IConstruct construct);

}
