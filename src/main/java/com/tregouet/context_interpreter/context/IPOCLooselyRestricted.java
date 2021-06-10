package com.tregouet.context_interpreter.context;

import java.util.Set;

public interface IPOCLooselyRestricted extends IPosetOfConstructs {
	
	Set<IPOCTightlyRestricted> getTightRestrictions();

}
