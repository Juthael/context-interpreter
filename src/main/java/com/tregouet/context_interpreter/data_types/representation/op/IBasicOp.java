package com.tregouet.context_interpreter.data_types.representation.op;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public interface IBasicOp extends IMonoBinderOp {
	
	IConstruct getConstruct();

}
