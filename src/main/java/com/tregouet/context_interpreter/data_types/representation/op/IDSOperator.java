package com.tregouet.context_interpreter.data_types.representation.op;

public interface IDSOperator {
	
	int getContextStateID();
	
	float getCost();
	
	int getID();
	
	int getNbOfApplications();
	
	int getNbOfBoundVar();
	
	void incCounters();

}
