package com.tregouet.context_interpreter.compiler_builder;

import java.util.List;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ITransition;

public interface IStateDeterministicTF extends ITransitionFunc {
	
	Set<IDeterministicTF> getDeterministicTFs();
	
	Set<Set<ITransition>> buidTransitionTrees(Set<ITransition> transFromStart);
	
	Set<Set<ITransition>> continueTree(Set<ITransition> currTransitions, Set<ITransition> currMaxTrans);
	
	void removeTunnelStates();
	
	ITransition reduce(List<ITransition> transitionChain);

}
