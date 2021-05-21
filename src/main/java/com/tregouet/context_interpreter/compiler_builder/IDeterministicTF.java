package com.tregouet.context_interpreter.compiler_builder;

import java.util.List;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ITransition;

public interface IDeterministicTF extends ITransitionFunc {
	
	void optimize();
	
	void setCosts();
	
	void buildMultiTapeOperators();
	
	void buildMultiBinderOperators();
	
	void buildConjunctiveOperators();
	
	Set<List<ITransition>> buildTransitionMaxChains();
	
	void incrementCounters(Set<List<ITransition>> maxChains);

}
