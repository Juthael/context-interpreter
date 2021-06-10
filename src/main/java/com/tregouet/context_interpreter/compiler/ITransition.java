package com.tregouet.context_interpreter.compiler;

import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public interface ITransition {
	
	boolean accept(IState state, ITapes tapes);
	
	void editEvaluationLog();
	
	IState getNextState();
	
	IDSOperator getOutput();
	
	IState getReadingState();
	
	void localizeAsStateRule();

}
