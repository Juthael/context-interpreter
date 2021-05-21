package com.tregouet.context_interpreter.compiler;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public interface IEvaluation {
	
	int getEvaluatingStateID();
	
	IConstruct getInput();
	
	IDSOperator getOutput();

}
