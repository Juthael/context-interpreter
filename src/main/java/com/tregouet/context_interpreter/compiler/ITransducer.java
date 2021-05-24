package com.tregouet.context_interpreter.compiler;

import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;

public interface ITransducer {
	
	IState getAcceptState();
	
	Set<IState> getCategoryStates();
	
	Set<IConstruct> getInputLanguage();
	
	Set<IState> getObjectStates();
	
	IState getOmegaState();
	
	Set<IState> getPreAcceptStates();
	
	IState getStartState();
	
	Set<IState> getStates();
	
	IState getStateWith(Set<IContextObject> extent);
	
	Set<ITransition> getTransitions();
	
	boolean hasStateWith(Set<IContextObject> extent);
	
	void reset();
	
	Set<IRepresentation> transduce(IConstruct construct);

}
