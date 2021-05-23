package com.tregouet.context_interpreter.data_types.construct.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.IVariable;
import com.tregouet.subseq_finder.ISymbolSeq;

public class Variable implements IVariable {

	public final static boolean DEFERRED_NAMING = true;
	
	private char name = ISymbolSeq.PLACEHOLDER.charAt(0);
	private int instantiations = 0;
	private static final List<Character> authorizedCharASCII = new ArrayList<Character>();
	private static Iterator<Character> charIte;
	
	public Variable(boolean lateNaming) {
		if (!lateNaming)
			setName();
	}

	public char getVarChar() {
		return name;
	}

	public void incrementInstantiationCount() {
		instantiations++;
	}

	public int getInstantiationCount() {
		return instantiations;
	}
	
	private char getNextChar() {
		if (!charIte.hasNext())
			charIte = authorizedCharASCII.iterator();
		return charIte.next();
	}
	
	public void setName() {
		if (authorizedCharASCII.isEmpty()) {
			setAuthorizedCharASCII();
			charIte = authorizedCharASCII.iterator();
		}
		name = getNextChar();	
	}
	
	private static void setAuthorizedCharASCII() {
		for (char curr = 'a' ; curr <= 'z' ; curr++) {
			authorizedCharASCII.add(curr);
		}
		for (char curr = 'α' ; curr <= 'ω' ; curr++) {
			authorizedCharASCII.add(curr);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name;
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
		Variable other = (Variable) obj;
		if (name != other.name)
			return false;
		return true;
	}

}
