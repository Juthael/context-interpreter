package com.tregouet.context_interpreter.compiler_builder;

import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.IState;
import com.tregouet.context_interpreter.compiler.ITransition;

public interface INonDeterministicTF extends ITransitionFunc {
	
	Set<ITransition> buildTransitions(Map<IState, Set<IState>> relOverStates);
	
	Set<IDeterministicTF> getDeterministicTFs();
	
	Set<IStateDeterministicTF> getStateDeterministicTFs();
	
	Set<Set<IState>> buildStateTreeSets(Set<IState> objectStates);

}
