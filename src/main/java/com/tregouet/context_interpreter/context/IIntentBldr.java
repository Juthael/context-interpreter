package com.tregouet.context_interpreter.context;

import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public interface IIntentBldr {
	
	Set<IConstruct> getIntent(Set<IContextObject> objects);

}
