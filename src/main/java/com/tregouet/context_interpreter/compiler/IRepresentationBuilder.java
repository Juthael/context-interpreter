package com.tregouet.context_interpreter.compiler;

import java.util.List;
import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;

public interface IRepresentationBuilder extends ITransducer {
	
	void calculateRepresentationCost();
	
	IRepresentation describe(List<IConstruct> constructs);
	
	IRepresentation doAbstract(List<IConstruct> constructs);
	
	IRepresentation getOutputLanguage();
	
	float getRepresentationCost();
	
	float howInformative(IConstruct construct);
	
	float howPrototypical(int objIndex, Set<Integer> subCtxtIdxes);
	
	float howProtoypical(int objIndex1);
	
	float howSimilar(Set<Integer> objIndexes);
	
	float howSimilarTo(int objIndex1, int objIndex2);
	
	IRepresentation inferAndAbstract(List<IConstruct> constructs);
	
	IRepresentation inferAndDescribe(IConstruct construct);

}
