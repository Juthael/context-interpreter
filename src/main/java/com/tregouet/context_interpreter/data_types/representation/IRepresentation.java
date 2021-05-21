package com.tregouet.context_interpreter.data_types.representation;

import java.util.LinkedHashMap;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public interface IRepresentation {
	
	float getCost();
	
	String asLambdaExpression();
	
	LinkedHashMap<IConstruct, IDSOperator> asGenusDifferentiaDesc();

}
