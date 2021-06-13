package com.tregouet.context_interpreter.context.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TreeFinder<T> {

	//non-reflexive
	private final Map<T, Set<T>> relation;
	private final Map<T, Set<T>> succRelation;
	private final T root;
	private final Set<T> rootSuccInExplLowerSet;
	private final Set<List<T>> permutationsOfSucc;
	private final Set<Map<T, Set<T>>> trees = new HashSet<Map<T, Set<T>>>();
	private final Set<T> attainableLeaves;
	
	public TreeFinder(Map<T, Set<T>> relation, Map<T, Set<T>> succRelation, T root, Set<T> explorableLowerSet) {
		this.relation = relation;
		this.succRelation = succRelation;
		this.root = root;		
		rootSuccInExplLowerSet = new HashSet<T>(succRelation.get(root));
		rootSuccInExplLowerSet.retainAll(explorableLowerSet);
		attainableLeaves = explorableLowerSet.stream()
							.filter(c -> succRelation.get(c).isEmpty())
							.collect(Collectors.toSet());
		permutationsOfSucc = permute(new ArrayList(rootSuccInExplLowerSet), rootSuccInExplLowerSet.size());
	}
	
	public Set<Map<T, Set<T>>> getTrees() {
		return trees;
	}
	
	private void setTreesForSpecifiedPermOfSuccessors(List<T> permutationOfSucc) {
		List<Set<T>> explorableLowerSets = getExplorableLowerSets(permutationOfSucc);
		Map<T, Set<Map<T, Set<T>>>> succToSubtrees = new HashMap<T, Set<Map<T, Set<T>>>>();
		for (int i = 0 ; i < permutationOfSucc.size() ; i++) {
			Set<Map<T, Set<T>>> treesFromCurrSucc = new HashSet<Map<T, Set<T>>>();
			if (!explorableLowerSets.get(i).isEmpty() || attainableLeaves.contains(permutationOfSucc.get(i))) {
				//HERE
			}
				
		}
	}
	
	//the returned list may contain empty sets
	private List<Set<T>> getExplorableLowerSets(List<T> successors) {
		List<Set<T>> explorableLSets = new ArrayList<Set<T>>();
		List<Set<T>> nonOverlappingLSets = getNonOverlappingLowerSets(successors);
		for (Set<T> nonOverlappingLSet : nonOverlappingLSets)
			explorableLSets.add(getUpperSetOfTheSetOfLeavesIn(nonOverlappingLSet));
		return explorableLSets;
	}
	
	//strict lower set
	private List<Set<T>> getNonOverlappingLowerSets(List<T> objects) {
		List<Set<T>> nonOverlappingLSets = new ArrayList<Set<T>>();
		Set<T> objToBeRemoved = new HashSet<T>();
		for (int i = 0 ; i < objects.size() ; i++) {
			Set<T> nonOverlappingLSet = getLowerSet(objects.get(i));
			nonOverlappingLSet.removeAll(objToBeRemoved);
			if (i < objects.size() - 1)
				objToBeRemoved.addAll(nonOverlappingLSet);
			nonOverlappingLSets.add(nonOverlappingLSet);				
		}
		return nonOverlappingLSets;
	}	
	
	private Set<T> getUpperSet(T o) {
		Set<T> upperBounds = new HashSet<T>();
		for (T curr : relation.keySet()) {
			if (relation.get(curr).contains(o))
			upperBounds.add(curr);
		}
		return upperBounds;
	}	
	
	private Set<T> getUpperSetOfTheSetOfLeavesIn(Set<T> subset) {	
		Set<T> leavesInSubset = subset.stream()
					.filter(i -> attainableLeaves.contains(i))
					.collect(Collectors.toSet());
		subset.retainAll(getUpperSet(leavesInSubset));
		return subset;
	}	
	
	private Set<T> getLowerSet(T o) {
		return new HashSet<T>(relation.get(o));
	}
	
	//returns empty set if param is empty
	private Set<T> getLowerSet(Set<T> objects) {
		if (!objects.isEmpty()) {
			Set<T> previousLowerBounds = null;
			Set<T> lowerBounds = null;
			Iterator<T> ite = objects.iterator();
			while (ite.hasNext()) {
				lowerBounds = getLowerSet(ite.next());
				if (previousLowerBounds != null) {
					lowerBounds.retainAll(previousLowerBounds);
				}
				if (ite.hasNext())
					previousLowerBounds = new HashSet<T>(lowerBounds);
			}
			return lowerBounds;
		}
		return new HashSet<T>();
	}	
	
	//returns empty set if param is empty
	private Set<T> getUpperSet(Set<T> objects) {
		if (!objects.isEmpty()) {
			Set<T> previousUpperBounds = null;
			Set<T> upperBounds = null;
			Iterator<T> ite = objects.iterator();
			while (ite.hasNext()) {
				upperBounds = getUpperSet(ite.next());
				if (previousUpperBounds != null) {
					upperBounds.retainAll(previousUpperBounds);
				}
				if (ite.hasNext())
					previousUpperBounds = new HashSet<T>(upperBounds);
			}
			return upperBounds;
		}
		return new HashSet<T>();
	}	
	
	//Heap's algorithm
	private Set<List<T>> permute(List<T> objects, int n){
		Set<List<T>> permutations = new HashSet<List<T>>();
		if (n == 1) {
			permutations.add(new ArrayList<T>(objects));
		}
		else {
			for (int i = 0 ; i < n ; i++) {
				permutations.addAll(permute(objects, n-1));
				if (n % 2 == 1)
					swap(objects, 0, n-1);
				else swap(objects, i, n-1);
			}
		}
		return permutations;
	}	
	
	private void swap(List<T> objects, int i, int j) {
		T swapped = objects.get(i);
		objects.set(i, objects.get(j));
		objects.set(j, swapped);
	}		

}
