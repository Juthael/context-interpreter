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
	private int rank = 0;
	private int type;
	
	public Category(Set<IConstruct> intent, Set<IContextObject> extent) {
		this.intent = intent;
		this.extent = extent;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (extent == null) {
			if (other.extent != null)
				return false;
		} else if (!extent.equals(other.extent))
			return false;
		if (intent == null) {
			if (other.intent != null)
				return false;
		} else if (!intent.equals(other.intent))
			return false;
		if (type != other.type)
			return false;
		return true;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extent == null) ? 0 : extent.hashCode());
		result = prime * result + ((intent == null) ? 0 : intent.hashCode());
		result = prime * result + type;
		return result;
	}

	public void setRank(int maxPathLengthFromMin) {
		rank = maxPathLengthFromMin;
	}

	public void setType(int type) {
		this.type = type;
	}

}
