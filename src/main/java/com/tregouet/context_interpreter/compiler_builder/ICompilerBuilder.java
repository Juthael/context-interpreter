package com.tregouet.context_interpreter.compiler_builder;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.compiler.IState;
import com.tregouet.context_interpreter.compiler.ITransducer;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;

public interface ICompilerBuilder {
	
	TreeSet<IDeterministicTF> buildDeterministicTFs(Set<IStateDeterministicTF> stateDetTFs);
	
	INonDeterministicTF buildNondeterministicTF(Map<IState, Set<IState>> relOverStates);
	
	Map<IState, Set<IState>> buildRelOverStates(Map<ICategory, Set<ICategory>> relOverCat);
	
	Set<IStateDeterministicTF> buildStateDeterministicTFs(INonDeterministicTF nonDetTF);
	
	ITransducer getNDTransducer();
	
	TreeSet<IRepresentation> getRepresentations();
	
	Set<ITransducer> getSDTransducers();

}
