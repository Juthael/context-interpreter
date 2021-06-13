package com.tregouet.context_interpreter.context.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.compiler.impl.Category;
import com.tregouet.context_interpreter.context.ILatticeBasedPOCat;
import com.tregouet.context_interpreter.context.IPOCLooselyRestricted;
import com.tregouet.context_interpreter.context.ITreeOfCategories;
import com.tregouet.context_interpreter.context.utils.IntentBldr;
import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.construct.ISymbol;
import com.tregouet.context_interpreter.data_types.construct.impl.AbstractConstruct;
import com.tregouet.context_interpreter.data_types.construct.impl.Terminal;
import com.tregouet.context_interpreter.data_types.construct.impl.Variable;
import com.tregouet.subseq_finder.ISubseqFinder;
import com.tregouet.subseq_finder.ISymbolSeq;
import com.tregouet.subseq_finder.impl.SubseqFinder;
import com.tregouet.subseq_finder.impl.SymbolSeq;

public class LatticeBasedPOCat extends PosetOfCategories implements ILatticeBasedPOCat {
	
	protected final Set<ICategory> lattice = new HashSet<ICategory>();
	protected ICategory latticeMax;
	protected final Set<ICategory> latticeAbstCat = new HashSet<ICategory>();
	protected final Set<ICategory> latticeObj = new HashSet<ICategory>();
	protected ICategory latticeMin;	

	public LatticeBasedPOCat(List<IContextObject> objects) {
		super(objects);
		AVariable.initializeNameProvider();
		buildCategoryLatticeStrictPartialOrderRelation();
		instantiateAcceptCategory();
		instantiatePreAcceptCategory();
		addAcceptAndPreAcceptCatToRelation();
		buildSuccessorRelation();
		buildPredecessorRelation();
		updateCategoryRanks();
	}
	
	@Override
	public Set<ICategory> getAllCategoriesExceptLatticeMinimum() {
		Set<ICategory> allButMin = new HashSet<>(relation.keySet());
		allButMin.remove(latticeMin);
		return allButMin;
	}
	
	@Override
	public Map<ICategory, Set<ICategory>> getLatticeSuccRel() {
		Map<ICategory, Set<ICategory>> latticeSuccRel = 
				new HashMap<ICategory, Set<ICategory>>(succRelation);
		latticeSuccRel.remove(accept);
		latticeSuccRel.remove(preAccept);
		return latticeSuccRel;
	}
	
	@Override
	public ICategory getLatticeMax() {
		return latticeMax;
	}
	
	@Override
	public ICategory getLatticeMin() {
		return latticeMin;
	}
	
	@Override
	public Map<IConstruct, ICategory> getConstructToCategoryMap() {
		//all categories but lattice minimum
		Set<ICategory> allCategoriesExceptMinimum = new HashSet<ICategory>(relation.keySet());
		allCategoriesExceptMinimum.remove(latticeMin);
		Map<IConstruct, ICategory> constructToCategory = new HashMap<IConstruct, ICategory>();
		for (ICategory category : allCategoriesExceptMinimum) {
			for (IConstruct construct : category.getIntent()) {
				constructToCategory.put(construct, category);
			}
		}
		return constructToCategory;
	}	
	
	@Override
	public Set<ICategory> getLattice(){
		return lattice;
	}	

	@Override
	public Set<ICategory> getLatticeAbstCategories(){
		return latticeAbstCat;
	}

	@Override
	public Set<ICategory> getLatticeAtoms() {
		return latticeObj;
	}

	@Override
	public IPOCLooselyRestricted getLooselyRestrictedPosetOfConstructs() {
		return new POCLooselyRestricted(this);
	}
	
	@Override
	public Set<ICategory> getObjectCategories() {
		return latticeObj;
	}
	
	@Override
	public Set<ITreeOfCategories> getTreesOfCategories() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Set<Map<ICategory, Set<ICategory>>> getSubTreesFrom(ICategory category, Set<ICategory> explorableLowerSet) {
		//set of alternative tree relations from this category
		Set<Map<ICategory, Set<ICategory>>> subTrees = new HashSet<Map<ICategory, Set<ICategory>>>();
		if (category.type() == ICategory.LATT_OBJ) {
			Map<ICategory, Set<ICategory>> leafMap = new HashMap<>();
			leafMap.put(category, new HashSet<ICategory>());
			subTrees.add(leafMap);
		}
		else {
			Set<ICategory> attainableSuccessors = getSuccessorsInSpecifiedSubset(category, explorableLowerSet);
			/*
			 * the set of attainable successors is the intersection of the set of the specified category's 
			 * successors in the relation over categories, with the specified explorable subset.
			 */
			Set<List<ICategory>> permutationsOfSuccessors = getPermutations(attainableSuccessors);
			/*
			 * The explorable lower set of a given successor may vary according to its index in the list of 
			 * successors (because of non-overlapping with previous successor's lower sets)
			 */
			for (List<ICategory> listOfSuccessors : permutationsOfSuccessors) {
				Map<ICategory, Set<ICategory>> subTreeForCurrPerm = new HashMap<ICategory, Set<ICategory>>();
				//each permutation yields one tree relation NON
				List<Set<ICategory>> explorableLowerSets = getExplorableLowerSets(listOfSuccessors);
				//ith set is the explorable lower set for the ith successor in the current list of successors
				for (int i = 0 ; i < listOfSuccessors.size() ; i++) {
					ICategory successor = listOfSuccessors.get(i);
					Set<ICategory> succExplorableLowerSet = explorableLowerSets.get(i);
					//a successor with an empty explorable lower set is either an object, or a dead-end
					if (!succExplorableLowerSet.isEmpty() || successor.type() == ICategory.LATT_OBJ) {
						//recursion
						for (Map<ICategory, Set<ICategory>> subTreeFromCurrSucc : 
								getSubTreesFrom(successor, succExplorableLowerSet))
							subTreeForCurrPerm.putAll(subTreeFromCurrSucc);
					}
				}
				//make specified cat the root of the sub-tree for current permutation of successors
				Set<ICategory> relatedToSpecCat = subTreeForCurrPerm.keySet();
				subTreeForCurrPerm.put(category, relatedToSpecCat);
				subTrees.add(subTreeForCurrPerm);
			}	
		}
		return subTrees;
	}
	
	private Set<ICategory> getSuccessorsInSpecifiedSubset(ICategory cat, Set<ICategory> subset){
		Set<ICategory> successorsInSubSet = getSuccessors(cat);
		successorsInSubSet.retainAll(subset);
		return successorsInSubSet;
	}
	
	
	
	
	private void addAcceptAndPreAcceptCatToRelation() {
		Set<ICategory> preAcceptSubCat = new HashSet<ICategory>(lattice);
		Set<ICategory> acceptSubCat = new HashSet<ICategory>(lattice);
		acceptSubCat.add(preAccept);
		relation.put(preAccept, preAcceptSubCat);
		relation.put(accept, acceptSubCat);
	}
	
	private void buildCategoryLatticeStrictPartialOrderRelation() {
		Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents = buildIntentToExtentRel();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : intentsToExtents.entrySet()) {
			ICategory category = new Category(entry.getKey(), entry.getValue());
			if (category.getExtent().size() == 1) {
				category.setType(Category.LATT_OBJ);
				latticeObj.add(category);
			}
			else if (category.getExtent().isEmpty()) {
				category.setType(Category.LATT_MIN);
				latticeMin = category;
			}
			else if (category.getExtent().size() == objects.size()) {
				category.setType(Category.LATT_MAX);
				latticeMax = category;
			}
			else {
				category.setType(Category.LATT_CAT);
				latticeAbstCat.add(category);
			}
			lattice.add(category);
			relation.put(category, new HashSet<ICategory>());
		}
		List<ICategory> catList = new ArrayList<ICategory>(lattice);
		for (int i = 0 ; i < catList.size() - 1 ; i++) {
			for (int j = i+1 ; j < catList.size() ; j++) {
				if (catList.get(i).getExtent().containsAll(catList.get(j).getExtent()))
					relation.get(catList.get(i)).add(catList.get(j));
				else if (catList.get(j).getExtent().containsAll(catList.get(i).getExtent()))
					relation.get(catList.get(j)).add(catList.get(i));
			}
		}
	}
	
	private Map<Set<IConstruct>, Set<IContextObject>> buildIntentToExtentRel() {
		Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents = 
				new HashMap<Set<IConstruct>, Set<IContextObject>>();
		Set<Set<IContextObject>> objectsPowerSet = buildObjectsPowerSet();
		for (Set<IContextObject> subset : objectsPowerSet) {
			Set<IConstruct> intent;
			if (subset.size() > 1)
				intent = IntentBldr.getIntent(subset);
			else if (subset.size() == 1)
				intent = new HashSet<IConstruct>(subset.iterator().next().getConstructs());
			else {
				intent = new HashSet<IConstruct>();
				for (IContextObject obj : objects)
					intent.addAll(obj.getConstructs());
			}
			if (intentsToExtents.containsKey(intent))
				intentsToExtents.get(intent).addAll(subset);
			else intentsToExtents.put(intent, subset);
		}
		intentsToExtents = singularizeConstructs(intentsToExtents);
		return intentsToExtents;
	}
	
	private Set<Set<IContextObject>> buildObjectsPowerSet() {
	    Set<Set<IContextObject>> powerSet = new HashSet<Set<IContextObject>>();
	    for (int i = 0; i < (1 << objects.size()); i++) {
	    	Set<IContextObject> subset = new HashSet<IContextObject>();
	        for (int j = 0; j < objects.size(); j++) {
	            if(((1 << j) & i) > 0)
	            	subset.add(objects.get(j));
	        }
	        powerSet.add(subset);
	    }
	    return powerSet;
	}	
	
	private void instantiateAcceptCategory() {
		ISymbol variable = new Variable(!AVariable.DEFERRED_NAMING);
		List<ISymbol> acceptProg = new ArrayList<ISymbol>();
		acceptProg.add(variable);
		IConstruct acceptConstruct = new AbstractConstruct(acceptProg);
		Set<IConstruct> acceptIntent =  new HashSet<IConstruct>();
		acceptIntent.add(acceptConstruct);
		accept = new Category(acceptIntent, new HashSet<IContextObject>(objects));
		accept.setType(ICategory.ACCEPT);
	}
	
	private void instantiatePreAcceptCategory() {
		Set<IConstruct> preAccIntent = new HashSet<IConstruct>();
		Set<IConstruct> lattMaxIntent = latticeMax.getIntent();
		List<ISymbolSeq> lattMaxSymbolSeq = new ArrayList<ISymbolSeq>();
		for (IConstruct construct : lattMaxIntent) {
			lattMaxSymbolSeq.add(new SymbolSeq(construct.toListOfStringsWithPlaceholders()));
		}
		ISubseqFinder subseqFinder = new SubseqFinder(lattMaxSymbolSeq);
		Set<ISymbolSeq> maxCommonSubsqs = subseqFinder.getMaxCommonSubseqs();
		for (ISymbolSeq subsq : maxCommonSubsqs) {
			List<ISymbol> preAccSymList = new ArrayList<ISymbol>();
			boolean lastSymStringWasPlaceholder = false;
			for (String symString : subsq.getStringSequence()) {
				if (symString.equals(ISymbolSeq.PLACEHOLDER)) {
					if (lastSymStringWasPlaceholder) {
						//do nothing. No use in consecutive placeholders.
					}
					else {
						preAccSymList.add(new Variable(AVariable.DEFERRED_NAMING));
						lastSymStringWasPlaceholder = true;
					}
				}
				else {
					preAccSymList.add(new Terminal(symString));
					lastSymStringWasPlaceholder = false;
				}
			}
			preAccIntent.add(new AbstractConstruct(preAccSymList));
		}
		for (IConstruct construct : preAccIntent)
			construct.singularize();
		preAccept = new Category(preAccIntent, new HashSet<IContextObject>(objects));
		preAccept.setType(ICategory.PREACCEPT);
	}	
	
	private Map<Set<IConstruct>, Set<IContextObject>> singularizeConstructs(
			Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents) {
		Map<Set<IConstruct>, Set<IContextObject>> mapWithSingularizedIntents 
			= new HashMap<Set<IConstruct>, Set<IContextObject>>();
		/*
		 * must use transitory collections not based on hash tables, since variable naming
		 * will modify hashcodes  
		 */
		List<Set<IConstruct>> listOfIntents = new ArrayList<Set<IConstruct>>();
		List<Set<IConstruct>> listOfSingularizedIntents = new ArrayList<Set<IConstruct>>();
		List<Set<IContextObject>> listOfExtents = new ArrayList<Set<IContextObject>>();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : intentsToExtents.entrySet()) {
			listOfIntents.add(entry.getKey());
			listOfExtents.add(entry.getValue());
		}
		for (Set<IConstruct> intent : listOfIntents) {
			Set<IConstruct> singularizedIntent = new HashSet<IConstruct>();
			for (IConstruct construct : intent) {
				construct.singularize();
				singularizedIntent.add(construct);
			}
			listOfSingularizedIntents.add(singularizedIntent);
		}
		for (int i = 0 ; i < listOfSingularizedIntents.size() ; i++) {
			mapWithSingularizedIntents.put(listOfSingularizedIntents.get(i), listOfExtents.get(i));
		}
		return mapWithSingularizedIntents;
	}

	private void updateCategoryRanks() {
		updateCategoryRanks(latticeMin, 0);
	}

}
