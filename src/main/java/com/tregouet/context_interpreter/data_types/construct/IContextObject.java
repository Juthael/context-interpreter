package com.tregouet.context_interpreter.data_types.construct;

import java.util.Iterator;
import java.util.List;

import com.tregouet.subseq_finder.ISymbolSeq;

public interface IContextObject {
	
	List<IConstruct> getConstructs();
	
	List<ISymbolSeq> toSymbolSeqs();
	
	Iterator<IConstruct> getIteratorOnConstructs();

}
