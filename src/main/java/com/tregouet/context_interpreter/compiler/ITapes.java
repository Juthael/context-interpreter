package com.tregouet.context_interpreter.compiler;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public interface ITapes {
	
	public static final boolean DESCRIPTION = false;
	public static final boolean ABSTRACTION = true;
	
	IState getPreviousState();
	
	boolean outputIsAbstract();
	
	void printOutput(IDSOperator operator);
	
	IConstruct read();
	
	void resetOutput();
	
	void setCurrentState(IState state);
	
	void setOutputType();
	
	void updateInputTape(IConstruct updatedTape);

}
