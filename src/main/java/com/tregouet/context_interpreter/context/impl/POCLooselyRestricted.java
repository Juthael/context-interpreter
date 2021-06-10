package com.tregouet.context_interpreter.context.impl;

import java.util.Set;

import com.tregouet.context_interpreter.context.IPOCLooselyRestricted;
import com.tregouet.context_interpreter.context.IPOCTightlyRestricted;
import com.tregouet.context_interpreter.context.IPosetOfCategories;

public class POCLooselyRestricted extends PosetOfConstructs implements IPOCLooselyRestricted {

	public POCLooselyRestricted(IPosetOfCategories catPoset) {
		super(catPoset);
	}

	@Override
	public Set<IPOCTightlyRestricted> getTightRestrictions() {
		// TODO Auto-generated method stub
		return null;
	}

}
