package com.tregouet.context_interpreter.data_types.representation.op;

import java.util.List;

import com.tregouet.context_interpreter.data_types.IVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public interface IMultiBinderOp {
	
	List<IVariable> getBoundVars();
	
	List<IConstruct> getConstructs();
	
	List<IMonoBinderOp> getMonoBinderCompnts();

}