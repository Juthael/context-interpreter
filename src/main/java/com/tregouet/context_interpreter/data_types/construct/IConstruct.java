package com.tregouet.context_interpreter.data_types.construct;

import java.util.Iterator;
import java.util.List;

import com.tregouet.subseq_finder.ISymbolSeq;

public interface IConstruct {
	
	List<ISymbol> getListOfSymbols();
	
	List<String> toListOfStrings();
	
	ISymbolSeq toSymbolSeq();
	
	Iterator<ISymbol> getIteratorOverSymbols();
	
	boolean meets(IConstruct constraint);
	
	boolean isAbstract();

}
