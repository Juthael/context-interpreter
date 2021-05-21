package com.tregouet.context_interpreter.compiler;

public interface IEvaluatingLog {
	
	void addEvaluation(IEvaluation eval);
	
	IEvaluation getLastEvaluation();
	
	String toString();

}
