package com.tregouet.context_interpreter.compiler_builder;

import java.util.Set;
import java.util.TreeSet;

import com.tregouet.context_interpreter.compiler.ITransducer;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;

public interface ICompilerBuilder {
	
	ITransducer getNDTransducer();
	
	TreeSet<IRepresentation> getRepresentations();
	
	Set<ITransducer> getSDTransducers();

}
