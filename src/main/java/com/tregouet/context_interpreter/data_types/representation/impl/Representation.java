package com.tregouet.context_interpreter.data_types.representation.impl;

import java.util.LinkedHashMap;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public abstract class Representation implements IRepresentation {

	@Override
	public int compareTo(IRepresentation o) {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
