package com.tregouet.context_interpreter.compiler;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public interface ITapes {
	
	public static final boolean DESCRIPTION = false;
	public static final boolean ABSTRACTION = true;
	
	IConstruct read();
	
	void updateInputTape(IConstruct updatedTape);
	
	void printOutput(IDSOperator operator);
	
	void resetOutput();
	
	void setCurrentState(IState state);
	
	IState getPreviousState();
	
	void setOutputType();
	
	boolean outputIsAbstract();

}
