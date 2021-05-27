package com.tregouet.context_interpreter.data_types.construct.impl;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.subseq_finder.ISymbolSeq;

public class Variable extends AVariable {
	
	private String name = ISymbolSeq.PLACEHOLDER;

	private int assignments = 0;

	public Variable(boolean deferredNaming) {
		if (!deferredNaming)
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public int getAssignmentCount() {
		return assignments;
	}

	public String getName() {
		return name;
	}

	/**
	 * Name and assignment values can't be used for the generation of a hashcode, because of 
	 * the possibility of late setting.  
	 */
	@Override
	public int hashCode() {
		return 1;
	}
	
	public void incrementAssignmentCount() {
		assignments++;
	}

	public void setName() {
		name = provideName();
	}
	
	@Override
	public String toString() {
		return name;
	}

}
