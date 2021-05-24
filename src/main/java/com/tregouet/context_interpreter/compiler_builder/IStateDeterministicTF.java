package com.tregouet.context_interpreter.compiler_builder;

import java.util.List;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ITransition;

public interface IStateDeterministicTF extends ITransitionFunc {
	
	Set<Set<ITransition>> buidTransitionTrees(Set<ITransition> transFromStart);
	
	Set<IDeterministicTF> getDeterministicTFs();
	
	ITransition reduce(List<ITransition> transitionChain);
	
	void removeTunnelStates();

}
