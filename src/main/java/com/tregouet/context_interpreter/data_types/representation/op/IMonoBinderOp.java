package com.tregouet.context_interpreter.data_types.representation.op;

import com.tregouet.context_interpreter.data_types.construct.AVariable;

public interface IMonoBinderOp extends IDSOperator {
	
	AVariable getBoundVar();
	
	@Override
	double getCost();

}
