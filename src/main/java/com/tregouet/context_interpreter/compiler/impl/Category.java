package com.tregouet.context_interpreter.compiler.impl;

import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public class Category implements ICategory {

	public static final int LATT_MIN = 0;
	public static final int LATT_OBJ = 1;
	public static final int LATT_CAT = 2;
	public static final int LATT_MAX = 3;
	public static final int PREACCEPT = 4;
	public static final int ACCEPT = 5;
	
	private final Set<IConstruct> intent;
	private final Set<IContextObject> extent;
	private int rank;
	private int type;
	
	public Category(Set<IConstruct> intent, Set<IContextObject> extent) {
		this.intent = intent;
		this.extent = extent;
	}

	public Set<IContextObject> getExtent() {
		return extent;
	}

	public Set<IConstruct> getIntent() {
		return intent;
	}

	public int getRank() {
		return rank;
	}

	public int getType() {
		return type;
	}

	public void setRank(int maxPathLengthFromMin) {
		rank = maxPathLengthFromMin;
	}

	public void setType(int type) {
		this.type = type;
	}

}
