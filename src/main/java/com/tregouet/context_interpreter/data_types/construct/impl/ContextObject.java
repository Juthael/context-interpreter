package com.tregouet.context_interpreter.data_types.construct.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.subseq_finder.ISymbolSeq;

public class ContextObject implements IContextObject {

	private static int lastID = 0;
	private final List<IConstruct> constructs;
	private final String iD;
	
	public ContextObject(List<IConstruct> constructs) {
		this.constructs = constructs;
		iD = "obj" + Integer.toString(lastID);
		lastID++;
	}

	public List<IConstruct> getConstructs() {
		return constructs;
	}
	
	public String getID() {
		return iD;
	}
	
	public Iterator<IConstruct> getIteratorOnConstructs() {
		return constructs.iterator();
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (IConstruct construct : constructs)
			sB.append(construct.toString() + System.lineSeparator());
		return sB.toString();
	}
	
	public List<ISymbolSeq> toSymbolSeqs(){
		List<ISymbolSeq> symbolSeqs = new ArrayList<ISymbolSeq>();
		for (IConstruct construct : constructs)
			symbolSeqs.add(construct.toSymbolSeq());
		return symbolSeqs;
	}

}
