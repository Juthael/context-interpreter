package com.tregouet.context_interpreter.data_types.construct;

import java.util.Iterator;
import java.util.List;

public interface IConstruct {
	
	Iterator<ISymbol> getIteratorOverSymbols();
	
	List<ISymbol> getListOfSymbols();
	
	int getNbOfTerminals();
	
	boolean isAbstract();
	
	boolean meets(IConstruct constraint);
	
	List<String> toListOfStringsWithPlaceholders();
	
	void singularize();

}
