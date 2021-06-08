package com.tregouet.context_interpreter.data_types.representation.op.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IBasicOp;
import com.tregouet.context_interpreter.data_types.representation.op.IConjunctiveOp;
import com.tregouet.context_interpreter.data_types.representation.op.IMonoBinderOp;
import com.tregouet.context_interpreter.data_types.representation.op.IMultiBinderOp;

public class MultiBinderOp extends DSOperator implements IMultiBinderOp {

	private final Set<IMonoBinderOp> monoBinders;
	
	public MultiBinderOp(int ctxtStateID, Set<IMonoBinderOp> monoBinders) {
		super(ctxtStateID);
		this.monoBinders = monoBinders;
	}

	@Override
	public int getNbOfBoundVar() {
		return monoBinders.size();
	}

	@Override
	public void incCounters() {
		for (IMonoBinderOp monoBinder : monoBinders)
			monoBinder.incCounters();
	}

	@Override
	public Set<AVariable> getBoundVars() {
		Set<AVariable> boundVars = new HashSet<AVariable>();
		for (IMonoBinderOp monoBinder : monoBinders)
			boundVars.add(monoBinder.getBoundVar());
		return boundVars;
	}

	@Override
	public Set<IConstruct> getValues() {
		Set<IConstruct> values = new HashSet<IConstruct>();
		for (IMonoBinderOp monoBinder : monoBinders) {
			if (monoBinder instanceof IConjunctiveOp)
				values.addAll(((IConjunctiveOp) monoBinder).getValues());
			else values.add(((IBasicOp) monoBinder).getValue());
		}
		return values;
	}

	@Override
	public Set<IMonoBinderOp> getMonoBinderComponents() {
		return monoBinders;
	}

	@Override
	public double getCost() {
		int assignmentCount = 0;
		int applicationCount = 0;
		for (IMonoBinderOp monobinder : monoBinders) {
			assignmentCount += monobinder.getBoundVar().getAssignmentCount();
			applicationCount += monobinder.getNbOfApplications();
		}
		return calculateCost(applicationCount, assignmentCount);
	}

}
