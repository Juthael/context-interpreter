package com.tregouet.context_interpreter.compiler.impl;

import com.tregouet.context_interpreter.compiler.IState;
import com.tregouet.context_interpreter.compiler.ITapes;
import com.tregouet.context_interpreter.compiler.ITransition;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public class Transition implements ITransition {

	private final IState fromState;
	private final IState toState;
	private final IConstruct acceptedInputTape;
	private final IConstruct nextInputTape;
	private final IDSOperator output;
	
	public Transition(IState fromState, IState toState, IConstruct acceptedInputTape, 
			IConstruct nextInputTape, IDSOperator output) {
		this.fromState = fromState;
		this.toState = toState;
		this.acceptedInputTape = acceptedInputTape;
		this.nextInputTape = nextInputTape;
		this.output = output;
	}

	@Override
	public boolean accept(IState state, ITapes tapes) {
		if (state == fromState && tapes.read().equals(acceptedInputTape)) {
			tapes.printOutput(output);
			editEvaluationLog();
			toState.acceptInput(tapes);
			return true;
		}
		return false;
	}

	@Override
	public void editEvaluationLog() {
		// TODO Auto-generated method stub

	}

	@Override
	public IState getNextState() {
		return toState;
	}

	@Override
	public IDSOperator getOutput() {
		return output;
	}

	@Override
	public IState getReadingState() {
		return toState;
	}

	@Override
	public void localizeAsStateRule() {
		fromState.addRule(this);
	}

}
