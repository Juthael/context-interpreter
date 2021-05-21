package com.tregouet.context_interpreter.data_types.representation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;
import com.tregouet.context_interpreter.data_types.representation.op.IMonoBinderOp;


public interface ITree extends IRepresentation {
	
	IDSOperator getToken();
	
	List<ITree> getChildren();
	
	Set<IDSOperator> getOperators();
	
	Set<IMonoBinderOp> getMonoBinderOp();
	
	void replaceMonoBinderOp(Map<IMonoBinderOp, IDSOperator> replacedToSubstitute);
	
	void incCounters();
	
	String asStringOfIDs();

}
