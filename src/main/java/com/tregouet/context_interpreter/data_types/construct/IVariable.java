package com.tregouet.context_interpreter.data_types.construct;

public interface IVariable extends ISymbol {

	int getInstantiationCount();
	
	char getVarChar();
	
	void incrementInstantiationCount();
	
}
