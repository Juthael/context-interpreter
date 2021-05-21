package com.tregouet.context_interpreter.data_types.representation.op;

import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public interface IConjunctiveOp extends IMonoBinderOp {
	
	List<IConstruct> getConstructs();

}
