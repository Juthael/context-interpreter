package com.tregouet.context_interpreter.data_types.construct;

import java.util.Iterator;
import java.util.List;

public interface IConstruct {
	
	public List<ISymbol> getListOfSymbols();
	
	public Iterator<ISymbol> getIteratorOverSymbols();
	
	boolean meets(IConstruct constraint);
	
	boolean isAbstract();

}
