package com.tregouet.context_interpreter.context;

import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public interface ICategoryBldr {
	
	Set<ICategory> getCategories(Set<IContextObject> objects);

}
