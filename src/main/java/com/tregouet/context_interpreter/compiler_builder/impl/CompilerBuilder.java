package com.tregouet.context_interpreter.compiler_builder.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.compiler.IState;
import com.tregouet.context_interpreter.compiler.ITransducer;
import com.tregouet.context_interpreter.compiler.impl.Category;
import com.tregouet.context_interpreter.compiler.impl.State;
import com.tregouet.context_interpreter.compiler_builder.ICompilerBuilder;
import com.tregouet.context_interpreter.compiler_builder.IDeterministicTF;
import com.tregouet.context_interpreter.compiler_builder.INonDeterministicTF;
import com.tregouet.context_interpreter.compiler_builder.IStateDeterministicTF;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;

public class CompilerBuilder implements ICompilerBuilder {

	private final Map<ICategory, Set<ICategory>> transitionRelOverCats;
	private final Set<IState> states;
	private final Map<IState, Set<IState>> relOverStates;
	private final INonDeterministicTF nonDeterministicTF;
	private final Set<IStateDeterministicTF> stateDeterministicTFs;
	private final TreeSet<IDeterministicTF> deterministicTFs;
	
	public CompilerBuilder(Map<ICategory, Set<ICategory>> transitionRelOverCats) {
		this.transitionRelOverCats = transitionRelOverCats;
		relOverStates = buildRelationOverStates();
		states = relOverStates.keySet();
		nonDeterministicTF = buildNonDeterministicTF();
		stateDeterministicTFs = buildStateDeterministicTFs();
		deterministicTFs = buildDeterministicTFs();
		
	}
	
	@Override
	public ITransducer getNDTransducer() {
		return null;
	}

	@Override
	public TreeSet<IRepresentation> getRepresentations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ITransducer> getSDTransducers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private TreeSet<IDeterministicTF> buildDeterministicTFs(){
		return null;
	}
	
	private INonDeterministicTF buildNonDeterministicTF() {
		return null;
	}
	
	private Map<IState, Set<IState>> buildRelationOverStates() {
		Map<IState, Set<IState>> relOverStates = new HashMap<IState, Set<IState>>();
		//build START state
		IState startState;
		ICategory startCategory;
		Set<IConstruct> startCatIntent = new HashSet<IConstruct>();
		for (ICategory cat : transitionRelOverCats.keySet()) {
			if (cat.type() == ICategory.LATT_OBJ)
				startCatIntent.addAll(cat.getIntent());
		}
		startCategory = new Category(startCatIntent, new HashSet<IContextObject>());
		startCategory.setType(ICategory.LATT_MIN);
		startState = new State(startCategory);
		//build state to category map, enter state relation keys
		Map<ICategory, IState> catToState = new HashMap<ICategory, IState>();
		for (ICategory cat : transitionRelOverCats.keySet()) {
			IState state = new State(cat);
			relOverStates.put(state, new HashSet<IState>());
			catToState.put(cat, state);
		}
		//build (reversed) relation over states
		for (ICategory superCat : transitionRelOverCats.keySet()) {
			IState subState = catToState.get(superCat);
			Set<IState> superStates = new HashSet<IState>();
			for (ICategory subCat : transitionRelOverCats.get(superCat))
				superStates.add(catToState.get(subCat));
			for (IState superState : superStates) {
				relOverStates.get(superState).add(subState);
			}
		}
		//add entry with START state
		Set<IState> objStates = new HashSet<IState>();
		for (IState state : relOverStates.keySet()) {
			if (state.type() == IState.OBJ_STATE)
				objStates.add(state);
		}
		relOverStates.put(startState, objStates);
		return relOverStates;
	}
	
	private Set<IStateDeterministicTF> buildStateDeterministicTFs() {
		return null;
	}

}
