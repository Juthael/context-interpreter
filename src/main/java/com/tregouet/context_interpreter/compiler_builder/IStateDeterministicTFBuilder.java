package com.tregouet.context_interpreter.compiler_builder;

import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.IState;

public interface IStateDeterministicTFBuilder {
	
	Set<IStateDeterministicTF> getStateDeterministicTFs();
	
	Set<Map<IState, Set<IState>>> buildTrees();
	
	void makePosetAtomistic();

}
