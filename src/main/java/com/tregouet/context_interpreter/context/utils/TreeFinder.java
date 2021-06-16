package com.tregouet.context_interpreter.context.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.tregouet.context_interpreter.context.IRelation;
import com.tregouet.context_interpreter.context.ITree;
import com.tregouet.context_interpreter.context.impl.Tree;

public class TreeFinder<T> {

	//non-reflexive
	private final IRelation<T> relation;
	private final T root;
	private final Set<T> rootSuccInExplLowerSet;
	private final Set<List<T>> permutationsOfSucc;
	private final Set<ITree<T>> trees = new HashSet<ITree<T>>();
	private final Set<T> attainableLeaves;
	
	public TreeFinder(IRelation<T> relation, T root, Set<T> explorableLowerSet) {
		this.relation = relation;
		this.root = root;		
		rootSuccInExplLowerSet = relation.getSuccessorsInSpecifiedSubset(root, explorableLowerSet);
		attainableLeaves = explorableLowerSet.stream()
							.filter(c -> relation.getMinimalElements().contains(c))
							.collect(Collectors.toSet());
		permutationsOfSucc = permute(new ArrayList<T>(rootSuccInExplLowerSet), rootSuccInExplLowerSet.size());
	}
	
	public Set<ITree<T>> getTrees() {
		return trees;
	}
	
	private Set<ITree<T>> getTreesForSpecifiedPermOfSuccessors(List<T> permutationOfSucc) {
		List<ITree<T>> trees = new ArrayList<ITree<T>>();
		List<Set<T>> explorableLowerSets = getExplorableLowerSets(permutationOfSucc);
		List<List<ITree<T>>> listOfSubTrees = new ArrayList<List<ITree<T>>>(); 
		for (int i = 0 ; i < permutationOfSucc.size() ; i++) {
			List<ITree<T>> treesFromCurrSucc = new ArrayList<ITree<T>>();
			//HERE may be several empty lists
			if (!explorableLowerSets.get(i).isEmpty()) {
				TreeFinder<T> tF = new TreeFinder<T>(relation, permutationOfSucc.get(i), explorableLowerSets.get(i));
				treesFromCurrSucc.addAll(tF.getTrees());
			}
			else if (attainableLeaves.contains(permutationOfSucc.get(i))) {
				treesFromCurrSucc.add(new Tree<T>(permutationOfSucc.get(i)));
			}
			//HERE
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
	private List<Set<T>> getNonOverlappingLowerSets(List<T> list) {
		List<Set<T>> nonOverlappingLSets = new ArrayList<Set<T>>();
		Set<T> objToBeRemoved = new HashSet<T>();
		for (int i = 0 ; i < list.size() ; i++) {
			Set<T> nonOverlappingLSet = relation.getLowerSet(list.get(i));
			nonOverlappingLSet.removeAll(objToBeRemoved);
			if (i < list.size() - 1)
				objToBeRemoved.addAll(nonOverlappingLSet);
			nonOverlappingLSets.add(nonOverlappingLSet);				
		}
		return nonOverlappingLSets;
	}
	
	private Set<T> getUpperSetOfTheSetOfLeavesIn(Set<T> subset) {	
		Set<T> leavesInSubset = subset.stream()
					.filter(i -> attainableLeaves.contains(i))
					.collect(Collectors.toSet());
		subset.retainAll(getUpperSet(leavesInSubset));
		return subset;
	}
	
	//returns empty set if param is empty
	private Set<T> getLowerSet(Set<T> elems) {
		if (!elems.isEmpty()) {
			Set<T> previousLowerBounds = null;
			Set<T> lowerBounds = null;
			Iterator<T> ite = elems.iterator();
			while (ite.hasNext()) {
				lowerBounds = relation.getLowerSet(ite.next());
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
	private Set<T> getUpperSet(Set<T> elems) {
		if (!elems.isEmpty()) {
			Set<T> previousUpperBounds = null;
			Set<T> upperBounds = null;
			Iterator<T> ite = elems.iterator();
			while (ite.hasNext()) {
				upperBounds = relation.getUpperSet(ite.next());
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
