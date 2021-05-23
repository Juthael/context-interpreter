package com.tregouet.context_interpreter.data_types.construct;

public interface IVariable extends ISymbol {

	char getVarChar();
	
	void incrementInstantiationCount();
	
	int getInstantiationCount();
	
}
