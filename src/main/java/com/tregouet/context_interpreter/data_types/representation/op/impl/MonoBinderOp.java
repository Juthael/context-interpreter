package com.tregouet.context_interpreter.data_types.representation.op.impl;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.representation.op.IMonoBinderOp;

public abstract class MonoBinderOp extends DSOperator implements IMonoBinderOp {

	protected final AVariable boundVar;
	
	public MonoBinderOp(int ctxtStateID, AVariable boundVar) {
		super(ctxtStateID);
		this.boundVar = boundVar;
	}

	@Override
	public AVariable getBoundVar() {
		return boundVar;
	}

	@Override
	public int getNbOfBoundVar() {
		return 1;
	}

	@Override
	public void incCounters() {
		boundVar.incAssignmentCount();
		applicationsCount++;
	}
	
	@Override
	public double getCost() {
		return calculateCost(applicationsCount, boundVar.getAssignmentCount());
	}

}
