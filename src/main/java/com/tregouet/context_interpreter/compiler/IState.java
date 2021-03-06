package com.tregouet.context_interpreter.compiler;

import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public interface IState {
	
	public static final int START_STATE = ICategory.LATT_MIN;
	public static final int OBJ_STATE = ICategory.LATT_OBJ;
	public static final int CAT_STATE = ICategory.LATT_CAT;
	public static final int OMEGA_STATE = ICategory.LATT_MAX;
	public static final int PRE_ACCEPT_STATE = ICategory.PREACCEPT;
	public static final int ACCEPT_STATE = ICategory.ACCEPT;
	
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
	
	int type();

}
