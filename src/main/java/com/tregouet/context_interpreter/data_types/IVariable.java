package com.tregouet.context_interpreter.data_types;

import com.tregouet.context_interpreter.data_types.construct.ISymbol;

public interface IVariable extends ISymbol {

	char getVarChar();
	
	void incrementInstantiationCount();
	
	int getInstantiationCount();
	
}
