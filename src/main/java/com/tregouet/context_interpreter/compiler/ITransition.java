package com.tregouet.context_interpreter.compiler;

import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public interface ITransition {
	
	boolean accept(IState state, ITapes tapes);
	
	IState getReadingState();
	
	IState getNextState();
	
	IDSOperator getOutput();
	
	void incrementCounters();
	
	void setCosts();
	
	void localizeAsStateRule();
	
	void editEvaluationLog();

}
