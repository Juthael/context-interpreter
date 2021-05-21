package com.tregouet.context_interpreter.data_types.representation.op;

public interface IDSOperator {
	
	float getCost();
	
	int getNbOfBoundVar();
	
	int getContextStateID();
	
	int getID();
	
	int getNbOfApplications();
	
	void incCounters();

}
