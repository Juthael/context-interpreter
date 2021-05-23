package com.tregouet.context_interpreter.data_types.construct.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.ISymbol;
import com.tregouet.context_interpreter.data_types.construct.IVariable;
import com.tregouet.subseq_finder.ISymbolSeq;
import com.tregouet.subseq_finder.impl.SymbolSeq;

public class Construct implements IConstruct {

	private final List<ISymbol> prog;
	
	public Construct(List<ISymbol> prog) {
		this.prog = prog;
	}
	
	public Construct(ISymbolSeq symSeq) {
		prog = new ArrayList<ISymbol>();
		for (String stringSym : symSeq.getStringSequence()) {
			if (stringSym.equals(ISymbolSeq.PLACEHOLDER))
				prog.add(new Variable(Variable.DEFERRED_NAMING));
			else prog.add(new Terminal(stringSym));
		}
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
	
	public List<String> toListOfStrings(){
		List<String> list = new ArrayList<String>();
		for (ISymbol sym : prog) {
			if (sym instanceof IVariable)
				list.add(ISymbolSeq.PLACEHOLDER);
			else list.add(sym.toString());
		}
		return list;
	}
	
	public ISymbolSeq toSymbolSeq() {
		return new SymbolSeq(toListOfStrings());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prog == null) ? 0 : prog.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Construct other = (Construct) obj;
		if (prog == null) {
			if (other.prog != null)
				return false;
		} else if (!prog.equals(other.prog))
			return false;
		return true;
	}

}
