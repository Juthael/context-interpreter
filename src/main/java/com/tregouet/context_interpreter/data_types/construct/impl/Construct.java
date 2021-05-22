package com.tregouet.context_interpreter.data_types.construct.impl;

import java.util.Iterator;
import java.util.List;

import com.tregouet.context_interpreter.data_types.IVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.ISymbol;

public class Construct implements IConstruct {

	private final List<ISymbol> prog;
	
	public Construct(List<ISymbol> prog) {
		this.prog = prog;
	}

	public boolean meets(IConstruct constraint) {
		Iterator<ISymbol> iteOnConstruct = this.getIteratorOverSymbols();
		for (ISymbol constraintSym : constraint.getListOfSymbols()) {
			if (!iteOnConstruct.hasNext())
				return false;
			while (iteOnConstruct.hasNext() && !iteOnConstruct.next().equals(constraintSym)) {
				//do nothing
			}
		}
		return true;
	}

	public boolean isAbstract() {
		for (ISymbol sym : prog) {
			if (sym instanceof IVariable)
				return true;
		}
		return false;
	}
	
	public Iterator<ISymbol> getIteratorOverSymbols(){
		return prog.iterator();
	}
	
	public List<ISymbol> getListOfSymbols(){
		return prog;
	}

}
