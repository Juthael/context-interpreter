package com.tregouet.context_interpreter.context.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.tregouet.context_interpreter.context.IRelation;

public class Relation<T> implements IRelation<T> {
	
	//elements sorted by the decreasing size of their lowerset
	private final TreeSet<T> set;
	//non-reflexive
	private final Map<T, Set<T>> relation;
	private final Map<T, Set<T>> succRelation = new HashMap<T, Set<T>>();
	private final Map<T, Set<T>> precRelation = new HashMap<T, Set<T>>();;
	
	public Relation(Map<T, Set<T>> relation) {
		this.relation = relation;
		set = new TreeSet<T>(Comparator.comparing(n -> -(this.relation.get(n).size())));
		setSuccRelation();
		setPrecRelation();
	}
	
	@Override
	public int compare(T elem1, T elem2) {
		if (relation.get(elem1).contains(elem2))
			return SUPER;
		else if (relation.get(elem2).contains(elem1))
			return SUB;
		else if (elem1.equals(elem2))
			return EQUALS;
		return UNCOMPARABLE;
	}
	
	@Override
	public Set<T> getSet(){
		return new HashSet<T>(set);
	}
	
	@Override
	public Set<T> getLowerSet(T elem){
		return new HashSet<T>(relation.get(elem));
	}
	
	@Override
	public Set<T> getLowerSet(Set<T> elems){
		Set<T> lowerBounds = new HashSet<T>();
		List<T> elemList = new ArrayList<T>(elems);
		for (int i = 0 ; i < elems.size() ; i++) {
			if (i == 0)
				lowerBounds.addAll(getLowerSet(elemList.get(i)));
			else lowerBounds.retainAll(getLowerSet(elemList.get(i)));
		}
		return lowerBounds;
	}
	
	@Override
	public Set<T> getUpperSet(T elem){
		Set<T> upperBounds = new HashSet<T>();
		for (T e : set) {
			if (relation.get(e).contains(elem))
			upperBounds.add(elem);
		}
		return upperBounds;
	}
	
	@Override
	public Set<T> getUpperSet(Set<T> elems) {
		Set<T> upperBounds = new HashSet<T>();
		List<T> elemList = new ArrayList<T>(elems);
		for (int i = 0 ; i < elems.size() ; i++) {
			if (i == 0)
				upperBounds.addAll(getUpperSet(elemList.get(i)));
			else upperBounds.retainAll(getUpperSet(elemList.get(i)));
		}
		return upperBounds;
	}	
	
	@Override
	public Set<T> getPredecessorsOf(T elem){
		return new HashSet<T>(precRelation.get(elem));
	}
	
	@Override
	public Set<T> getSuccessorsOf(T elem) {
		return new HashSet<T>(succRelation.get(elem));
	}
	
	@Override
	public Set<List<T>> getSpanningChainsFrom(T root){
		Set<List<T>> chains = new HashSet<List<T>>();
		if (!succRelation.get(root).isEmpty()) {
			Set<List<T>> subchains = new HashSet<List<T>>();
			for (T succ : succRelation.get(root)) {
				subchains.addAll(getSpanningChainsFrom(succ));
			}
			for (List<T> chain : subchains) {
				chain.add(0, root);
				chains.add(chain);
			}
		}
		else {
			List<T> chain = new ArrayList<T>();
			chain.add(root);
			chains.add(chain);
		}
		return chains;
	}	
	
	private void setSuccRelation() {
		for (T key : set) {
			succRelation.put(key, new HashSet<T>(relation.get(key)));
		}
		Iterator<T> upperIte = set.iterator();
		while (upperIte.hasNext()) {
			T upper = upperIte.next();
			Iterator<T> lowerIte = set.iterator();
			T lower = lowerIte.next();
			while (lower != upper) {
				if (succRelation.get(lower).contains(upper))
					succRelation.get(lower).removeAll(relation.get(upper));
				lower = lowerIte.next();
			}
		}
	}
	
	//successor relation must be set beforehand
	private void setPrecRelation() {
		for (T elem : set) {
			precRelation.put(elem, new HashSet<T>());
		}
		for (T predecessor : set) {
			for (T successor : succRelation.get(predecessor)) {
				precRelation.get(successor).add(predecessor);
			}
		}
	}

	@Override
	public Map<T, Set<T>> getRelationMap() {
		Map<T, Set<T>> relationMap = new HashMap<>();
		for (Entry<T, Set<T>> entry : relation.entrySet()) {
			relationMap.put(entry.getKey(), new HashSet<T>(entry.getValue()));
		}
		return relationMap;
	}

	@Override
	public Map<T, Set<T>> getSuccRelationMap() {
		Map<T, Set<T>> succRelationMap = new HashMap<>();
		for (Entry<T, Set<T>> entry : succRelation.entrySet()) {
			succRelationMap.put(entry.getKey(), new HashSet<T>(entry.getValue()));
		}
		return succRelationMap;
	}

	@Override
	public Map<T, Set<T>> getPrecRelationMap() {
		Map<T, Set<T>> precRelationMap = new HashMap<>();
		for (Entry<T, Set<T>> entry : precRelation.entrySet()) {
			precRelationMap.put(entry.getKey(), new HashSet<T>(entry.getValue()));
		}
		return precRelationMap;
	}

	@Override
	public Set<T> get(T elem) {
		return new HashSet<>(relation.get(elem));
	}

	@Override
	public void addAsMaximum(T max) {
		Set<T> related = new HashSet<T>(set);
		relation.put(max, related);
		set.add(max);
		succRelation.put(max, new HashSet<T>());
		precRelation.put(max, getMaximalElements());
	}

	@Override
	public Set<T> getMaximalElements() {
		Set<T> maxElem = new HashSet<T>(set);
		relation.values().stream().forEach(n -> maxElem.removeAll(n));
		return maxElem;
	}

	@Override
	public Set<T> getMinimalElements() {
		Set<T> minElem = set.stream()
							.filter(n -> relation.get(n).isEmpty())
							.collect(Collectors.toSet());
		return minElem;
	}

}
