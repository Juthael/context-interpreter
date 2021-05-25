package com.tregouet.context_interpreter.data_types.representation.op;

import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;

public interface IMultiBinderOp {
	
	List<AVariable> getBoundVars();
	
	List<IConstruct> getConstructs();
	
	List<IMonoBinderOp> getMonoBinderCompnts();

}
