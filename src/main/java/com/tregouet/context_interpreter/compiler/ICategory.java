package com.tregouet.context_interpreter.compiler;

import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public interface ICategory {
	
	public static final int LATT_MIN = 0;
	public static final int LATT_OBJ = 1;
	public static final int LATT_CAT = 2;
	public static final int LATT_MAX = 3;
	public static final int PREACCEPT = 4;
	public static final int ACCEPT = 5;
	
	Set<IContextObject> getExtent();
	
	Set<IConstruct> getIntent();
	
	int rank();
	
	void setRank(int maxPathLengthFromMin);
	
	void setType(int type);
	
	int type();

}
