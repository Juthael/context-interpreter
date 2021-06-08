package com.tregouet.context_interpreter.data_types.representation.op.impl;

import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IConjunctiveOp;

public class ConjunctiveOp extends MonoBinderOp implements IConjunctiveOp {

	private final Set<IConstruct> values;
	
	public ConjunctiveOp(int ctxtStateID, AVariable boundVar, Set<IConstruct> values) {
		super(ctxtStateID, boundVar);
		this.values = values;
	}
	
	@Override
	public Set<IConstruct> getValues() {
		return values;
	}

}
