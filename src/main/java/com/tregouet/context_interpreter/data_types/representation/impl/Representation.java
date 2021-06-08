package com.tregouet.context_interpreter.data_types.representation.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public abstract class Representation implements IRepresentation, Comparable<IRepresentation> {

	@Override
	public int compareTo(IRepresentation other) {
		return Double.compare(getCost(), other.getCost());
	}

	@Override
	public LinkedHashMap<IConstruct, IDSOperator> asGenusDifferentiaDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String asLambdaExpression() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<AVariable, Set<IConstruct>> asDomainSpecificGrammar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
