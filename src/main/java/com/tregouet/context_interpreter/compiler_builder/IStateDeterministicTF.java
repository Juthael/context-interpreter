package com.tregouet.context_interpreter.compiler_builder;

import java.util.List;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ITransition;

public interface IStateDeterministicTF extends ITransitionFunc {
	
	Set<IDeterministicTF> getDeterministicTFs();
	
	Set<Set<ITransition>> buidTransitionTrees(Set<ITransition> transFromStart);
	
	void removeTunnelStates();
	
	ITransition reduce(List<ITransition> transitionChain);

}
