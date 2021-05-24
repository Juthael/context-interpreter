package com.tregouet.context_interpreter.compiler_builder;

import java.util.List;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ITransition;

public interface IDeterministicTF extends ITransitionFunc {
	
	void buildConjunctiveOperators();
	
	void buildMultiBinderOperators();
	
	void buildMultiTapeOperators();
	
	Set<List<ITransition>> buildTransitionMaxChains();
	
	void incrementCounters(Set<List<ITransition>> maxChains);
	
	void optimize();
	
	void setCosts();

}
