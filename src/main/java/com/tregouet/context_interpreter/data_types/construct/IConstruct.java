package com.tregouet.context_interpreter.data_types.construct;

public interface IConstruct {
	
	boolean meets(IConstruct constraint);
	
	boolean isAbstract();

}
