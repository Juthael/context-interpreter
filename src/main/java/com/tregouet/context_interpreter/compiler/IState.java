package com.tregouet.context_interpreter.compiler;

import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public interface IState {
	
	public static final int START_STATE = 0;
	public static final int OBJ_STATE = 1;
	public static final int CAT_STATE = 2;
	public static final int OMEGA_STATE = 3;
	public static final int PRE_ACCEPT_STATE = 4;
	public static final int ACCEPT_STATE = 5;
	
	boolean acceptInput(ITapes tapes);
	
	void acceptStateInputLanguage();
	
	void addRule(ITransition transition);
	
	void generateOutputLanguage();
	
	Set<IContextObject> getExtent();
	
	Set<IConstruct> getIntent();
	
	int getRank();
	
	int getStateID();
	
	boolean isActive();
	
	void mergeTapesWithSameInput();
	
	void proceedTransitions();
	
	void reset();
	
	void setRank(int rank);

}
