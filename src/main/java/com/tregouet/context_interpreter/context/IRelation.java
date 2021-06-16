package com.tregouet.context_interpreter.context;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRelation<T> {
	
	public static final int UNCOMPARABLE = -2;
	public static final int SUB = -1;
	public static final int EQUALS = 0;
	public static final int SUPER = 1;	
	
	int compare(T elem1, T elem2);

	Set<T> get(T elem);
	
	Set<T> getSet();

	Set<T> getLowerSet(T elem);

	Set<T> getLowerSet(Set<T> elems);

	Set<T> getUpperSet(T elem);
	
	Set<T> getUpperSet(Set<T> elems);

	Set<T> getPredecessorsOf(T elem);

	Set<T> getSuccessorsOf(T elem);
	
	Set<T> getSuccessorsInSpecifiedSubset(T elem, Set<T> subset);

	Set<List<T>> getSpanningChainsFrom(T root);
	
	Map<T, Set<T>> getRelationMap();
	
	Map<T, Set<T>> getSuccRelationMap();
	
	Map<T, Set<T>> getPrecRelationMap();
	
	Set<T> getMaximalElements();
	
	Set<T> getMinimalElements();
	
	Set<ITree<T>> getAllMaxSpanningTrees();
	
	void addAsMaximum(T max);
	
	IRelation<T> restrictTo(Set<T> subset);

}
