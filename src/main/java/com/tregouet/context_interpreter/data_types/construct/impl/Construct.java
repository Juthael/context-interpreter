package com.tregouet.context_interpreter.data_types.construct.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.ISymbol;
import com.tregouet.context_interpreter.data_types.construct.ITerminal;
import com.tregouet.subseq_finder.ISymbolSeq;
import com.tregouet.subseq_finder.impl.SymbolSeq;

public class Construct implements IConstruct {

	private final List<ISymbol> prog;
	private final int nbOfTerminals;
	
	public Construct(ISymbolSeq symSeq) {
		prog = new ArrayList<ISymbol>();
		for (String stringSym : symSeq.getStringSequence()) {
			if (stringSym.equals(ISymbolSeq.PLACEHOLDER))
				prog.add(new Variable(Variable.DEFERRED_NAMING));
			else prog.add(new Terminal(stringSym));
		}
		int nbOfTerminals = 0;
		for (ISymbol symbol : prog) {
			if (symbol instanceof ITerminal)
				nbOfTerminals++;
		}
		this.nbOfTerminals = nbOfTerminals;
	}
	
	public Construct(List<ISymbol> prog) {
		this.prog = prog;
		int nbOfTerminals = 0;
		for (ISymbol symbol : prog) {
			if (symbol instanceof ITerminal)
				nbOfTerminals++;
		}
		this.nbOfTerminals = nbOfTerminals;
	}
	
	public Construct(String[] progStrings) {
		prog = new ArrayList<ISymbol>();
		for (String symString : progStrings) {
			if (symString.equals(ISymbolSeq.PLACEHOLDER))
				prog.add(new Variable(Variable.DEFERRED_NAMING));
			else prog.add(new Terminal(symString));
		}
		int nbOfTerminals = 0;
		for (ISymbol symbol : prog) {
			if (symbol instanceof ITerminal)
				nbOfTerminals++;
		}
		this.nbOfTerminals = nbOfTerminals;
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

	public Iterator<ISymbol> getIteratorOverSymbols(){
		return prog.iterator();
	}
	
	public List<ISymbol> getListOfSymbols(){
		return prog;
	}
	
	@Override
	public int getNbOfTerminals() {
		return nbOfTerminals;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prog == null) ? 0 : prog.hashCode());
		return result;
	}
	
	public boolean isAbstract() {
		for (ISymbol sym : prog) {
			if (sym instanceof AVariable)
				return true;
		}
		return false;
	}

	public boolean meets(IConstruct constraint) {
		if (prog.size() > constraint.getListOfSymbols().size()) {
			List<ISymbol> constructSymbols = this.getListOfSymbols();
			List<ISymbol> constraintSymbols = constraint.getListOfSymbols();
			int constraintIdx = 0;
			for (int constructIdx = 0 ; constructIdx < constructSymbols.size() && constraintIdx < constraintSymbols.size() ; constructIdx++) {
				if (constructSymbols.get(constructIdx).equals(constraintSymbols.get(constraintIdx)))
					constraintIdx++;
			}
			if (constraintIdx == constraintSymbols.size())
				return true;
		}
		return false;
	}

	public List<String> toListOfStrings(){
		List<String> list = new ArrayList<String>();
		for (ISymbol sym : prog) {
			if (sym instanceof AVariable)
				list.add(ISymbolSeq.PLACEHOLDER);
			else list.add(sym.toString());
		}
		return list;
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (int i = 0 ; i < prog.size() ; i++) {
			sB.append(prog.get(i));
			if (i < prog.size() - 1)
				sB.append(" ");
		}
		return sB.toString();
	}

	public ISymbolSeq toSymbolSeq() {
		return new SymbolSeq(toListOfStrings());
	}

}
