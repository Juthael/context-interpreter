package com.tregouet.context_interpreter.data_types.construct;

import java.util.Iterator;
import java.util.List;

public interface IContextObject {
	
	List<IConstruct> getConstructs();
	
	Iterator<IConstruct> getIteratorOnConstructs();

}
