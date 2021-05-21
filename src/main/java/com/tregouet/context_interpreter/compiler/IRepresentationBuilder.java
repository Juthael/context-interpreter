package com.tregouet.context_interpreter.compiler;

import java.util.List;
import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;

public interface IRepresentationBuilder extends ITransducer {
	
	IRepresentation describe(List<IConstruct> constructs);
	
	IRepresentation inferAndDescribe(IConstruct construct);
	
	IRepresentation doAbstract(List<IConstruct> constructs);
	
	IRepresentation inferAndAbstract(List<IConstruct> constructs);
	
	float howSimilar(Set<Integer> objIndexes);
	
	float howSimilarTo(int objIndex1, int objIndex2);
	
	float howProtoypical(int objIndex1);
	
	float howPrototypical(int objIndex, Set<Integer> subCtxtIdxes);
	
	float howInformative(IConstruct construct);
	
	void calculateRepresentationCost();
	
	float getRepresentationCost();
	
	IRepresentation getOutputLanguage();

}
