package com.tregouet.context_interpreter.compiler.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.compiler.IState;
import com.tregouet.context_interpreter.compiler.ITapes;
import com.tregouet.context_interpreter.compiler.ITransition;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;

public class State implements IState {

	private static int lastID = 0;
	
	private final ICategory category;
	private Set<ITransition> rules = new HashSet<ITransition>();
	private final Set<ITapes> currTapes = new HashSet<>();
	private int rank = -1;
	private int iD = lastID++;
	
	public State(ICategory category) {
		this.category = category;
	}
	
	@Override
	public boolean acceptInput(ITapes tapes) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptStateInputLanguage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addRule(ITransition transition) {
		rules.add(transition);
	}

	@Override
	public void generateOutputLanguage() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<IContextObject> getExtent() {
		return category.getExtent();
	}

	@Override
	public Set<IConstruct> getIntent() {
		return category.getIntent();
	}

	@Override
	public int getRank() {
		return rank;
	}

	@Override
	public int getStateID() {
		return iD;
	}

	@Override
	public boolean isActive() {
		return (!currTapes.isEmpty());
	}

	@Override
	public void mergeTapesWithSameInput() {
		// TODO Auto-generated method stub
	}

	@Override
	public void proceedTransitions() {
		// TODO Auto-generated method stub
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int type() {
		return category.type();
	}

}
