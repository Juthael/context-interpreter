package com.tregouet.context_interpreter.compiler_builder.impl;

import com.tregouet.context_interpreter.compiler.IState;
import com.tregouet.context_interpreter.compiler.ITapes;
import com.tregouet.context_interpreter.compiler.ITransition;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public class Transition implements ITransition {

	@Override
	public boolean accept(IState state, ITapes tapes) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void editEvaluationLog() {
		// TODO Auto-generated method stub

	}

	@Override
	public IState getNextState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDSOperator getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IState getReadingState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void localizeAsStateRule() {
		// TODO Auto-generated method stub

	}

}
