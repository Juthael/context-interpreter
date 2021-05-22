package com.tregouet.context_interpreter.data_types.construct.impl;

import java.util.Iterator;
import java.util.List;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public class ContextObject implements IContextObject {

	private final List<IConstruct> constructs;
	private final String iD;
	private static int lastID = 0;
	
	public ContextObject(List<IConstruct> constructs) {
		this.constructs = constructs;
		iD = "obj" + Integer.toString(lastID);
		lastID++;
	}

	public List<IConstruct> getConstructs() {
		return constructs;
	}
	
	public String getID() {
		return iD;
	}

	public Iterator<IConstruct> getIteratorOnConstructs() {
		return constructs.iterator();
	}

}
