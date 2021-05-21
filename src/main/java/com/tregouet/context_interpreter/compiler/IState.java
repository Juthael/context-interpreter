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
	
	Set<IConstruct> getIntent();
	
	Set<IContextObject> getExtent();
	
	int getStateID();
	
	boolean acceptInput(ITapes tapes);
	
	void acceptStateInputLanguage();
	
	void proceedTransitions();
	
	int getRank();
	
	void generateOutputLanguage();
	
	void reset();
	
	boolean isActive();
	
	void addRule(ITransition transition);
	
	void setRank(int rank);
	
	void mergeTapesWithSameInput();

}
