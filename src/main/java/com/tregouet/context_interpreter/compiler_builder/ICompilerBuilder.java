package com.tregouet.context_interpreter.compiler_builder;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.compiler.IState;
import com.tregouet.context_interpreter.compiler.ITransducer;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;

public interface ICompilerBuilder {
	
	ITransducer getNDTransducer();
	
	Set<ITransducer> getSDTransducers();
	
	TreeSet<IRepresentation> getRepresentations();
	
	Map<IState, Set<IState>> buildRelOverStates(Map<ICategory, Set<ICategory>> relOverCat);
	
	INonDeterministicTF buildNondeterministicTF(Map<IState, Set<IState>> relOverStates);
	
	Set<IStateDeterministicTF> buildStateDeterministicTFs(INonDeterministicTF nonDetTF);
	
	TreeSet<IDeterministicTF> buildDeterministicTFs(Set<IStateDeterministicTF> stateDetTFs);

}
