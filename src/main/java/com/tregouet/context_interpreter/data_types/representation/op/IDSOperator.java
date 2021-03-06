package com.tregouet.context_interpreter.data_types.representation.op;

public interface IDSOperator {
	
	int getContextStateID();
	
	double getCost();
	
	int getID();
	
	int getNbOfApplications();
	
	int getNbOfBoundVar();
	
	void incCounters();

}
