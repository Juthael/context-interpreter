package com.tregouet.context_interpreter.data_types.representation.op.impl;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IBasicOp;

public class BasicOp extends MonoBinderOp implements IBasicOp {

	private IConstruct value;
	
	public BasicOp(int ctxtStateID, AVariable boundVar, IConstruct value) {
		super(ctxtStateID, boundVar);
		this.value = value;
	}

	@Override
	public IConstruct getValue() {
		return value;
	}

}
