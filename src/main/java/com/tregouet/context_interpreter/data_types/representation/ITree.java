package com.tregouet.context_interpreter.data_types.representation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;
import com.tregouet.context_interpreter.data_types.representation.op.IMonoBinderOp;


public interface ITree extends IRepresentation {
	
	String asStringOfIDs();
	
	List<ITree> getChildren();
	
	Set<IMonoBinderOp> getMonoBinderOp();
	
	Set<IDSOperator> getOperators();
	
	IDSOperator getToken();
	
	void incCounters();
	
	void replaceMonoBinderOp(Map<IMonoBinderOp, IDSOperator> replacedToSubstitute);

}
