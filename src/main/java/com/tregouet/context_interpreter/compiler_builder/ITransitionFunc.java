package com.tregouet.context_interpreter.compiler_builder;

import java.util.Set;

import com.tregouet.context_interpreter.compiler.IState;
import com.tregouet.context_interpreter.compiler.ITransition;

public interface ITransitionFunc {
	
	Set<IState> getStates();
	
	Set<ITransition> getTransitions();

}
