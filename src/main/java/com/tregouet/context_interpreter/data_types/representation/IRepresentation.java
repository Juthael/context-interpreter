package com.tregouet.context_interpreter.data_types.representation;

import java.util.LinkedHashMap;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public interface IRepresentation extends Comparable<IRepresentation> {
	
	LinkedHashMap<IConstruct, IDSOperator> asGenusDifferentiaDesc();
	
	String asLambdaExpression();
	
	float getCost();

}
