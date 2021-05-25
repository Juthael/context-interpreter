package com.tregouet.context_interpreter.data_types.construct.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.IVariable;
import com.tregouet.subseq_finder.ISymbolSeq;

public class Variable implements IVariable {

	public final static boolean DEFERRED_NAMING = true;
	
	private static final List<Character> authorizedCharASCII = new ArrayList<Character>();
	private static Iterator<Character> charIte;
	private char name = ISymbolSeq.PLACEHOLDER.charAt(0);
	
	private int instantiations = 0;

	public Variable(boolean lateNaming) {
		if (!lateNaming)
			setName();
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

	public int getAssignmentCount() {
		return instantiations;
	}
	
	public char getVarChar() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name;
		return result;
	}
	
	public void incrementAssignmentCount() {
		instantiations++;
	}

	public void setName() {
		if (authorizedCharASCII.isEmpty()) {
			for (char curr = 'a' ; curr <= 'z' ; curr++) {
				authorizedCharASCII.add(curr);
			}
			for (char curr = 945 ; curr <= 965 ; curr++) {
				authorizedCharASCII.add(curr);
			}
			charIte = authorizedCharASCII.iterator();
		}
		name = getNextChar();	
	}

	@Override
	public String toString() {
		return Character.toString(name);
	}
	
	private char getNextChar() {
		if (!charIte.hasNext())
			charIte = authorizedCharASCII.iterator();
		return charIte.next();
	}

}
