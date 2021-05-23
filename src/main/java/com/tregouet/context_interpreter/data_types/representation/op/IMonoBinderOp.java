package com.tregouet.context_interpreter.data_types.representation.op;

import com.tregouet.context_interpreter.data_types.construct.IVariable;

public interface IMonoBinderOp extends IDSOperator {
	
	IVariable getBoundVar();

}
