package com.tregouet.context_interpreter.data_types.representation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public interface IRepresentation extends Comparable<IRepresentation> {
	
	Map<AVariable, Set<IConstruct>> asDomainSpecificGrammar();
	
	LinkedHashMap<IConstruct, IDSOperator> asGenusDifferentiaDesc();
	
	String asLambdaExpression();
	
	float getCost();

}
