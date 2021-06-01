package com.tregouet.context_interpreter.data_types.construct;

import java.util.Iterator;
import java.util.List;

import com.tregouet.subseq_finder.ISymbolSeq;

public interface IConstruct {
	
	Iterator<ISymbol> getIteratorOverSymbols();
	
	List<ISymbol> getListOfSymbols();
	
	int getNbOfTerminals();
	
	boolean isAbstract();
	
	boolean meets(IConstruct constraint);
	
	List<String> toListOfStrings();
	
	ISymbolSeq toSymbolSeq();

}
