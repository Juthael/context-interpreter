package com.tregouet.context_interpreter.context;

import java.util.Set;

public interface ITreeOfConstructs extends IPosetOfConstructs {
	
	Set<ITreeOfConstructs> getTreesOfConstructs();

}
