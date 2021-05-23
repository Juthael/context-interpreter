package com.tregouet.context_interpreter.data_types.representation.op;

import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IVariable;

public interface IMultiBinderOp {
	
	List<IVariable> getBoundVars();
	
	List<IConstruct> getConstructs();
	
	List<IMonoBinderOp> getMonoBinderCompnts();

}
