package com.tregouet.context_interpreter.data_types.construct.impl;

import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public class Construct extends AbstractConstruct implements IConstruct {

	private static int LAST_ID = 0;
	private int iD = 0;
	
	public Construct(List<String> constructAsListOfStr) {
		super(constructAsListOfStr.toArray(new String[constructAsListOfStr.size()]));
	}
	
	public Construct(String[] progStrings) {
		super(progStrings);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Construct other = (Construct) obj;
		if (iD != other.iD)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + iD;
		return result;
	}

	@Override
	public void singularize() {
		iD = LAST_ID++;
	}
	
	@Override
	public boolean isAbstract() {
		return false;
	}

}
