package com.tregouet.context_interpreter.compiler_builder;

import java.util.Set;

import com.tregouet.context_interpreter.compiler.ITransition;

public interface ITransitionsBuilder {
	
	Set<ITransition> getTransitions();

}
