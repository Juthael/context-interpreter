package com.tregouet.context_interpreter.data_types.representation.op;

import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public interface IMultiBinderOp extends IDSOperator {
	
	Set<AVariable> getBoundVars();
	
	@Override
	double getCost();
	
	Set<IMonoBinderOp> getMonoBinderComponents();
	
	Set<IConstruct> getValues();

}
