package com.tregouet.context_interpreter.compiler;

import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;

public interface ITransducer {
	
	void reset();
	
	Set<IRepresentation> transduce(IConstruct construct);
	
	Set<IState> getStates();
	
	IState getStateWith(Set<IContextObject> extent);
	
	boolean hasStateWith(Set<IContextObject> extent);
	
	Set<ITransition> getTransitions();
	
	IState getStartState();
	
	IState getAcceptState();
	
	Set<IState> getPreAcceptStates();
	
	Set<IState> getObjectStates();
	
	IState getOmegaState();
	
	Set<IState> getCategoryStates();
	
	Set<IConstruct> getInputLanguage();

}
