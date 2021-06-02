package com.tregouet.context_interpreter.context.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.compiler.impl.Category;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.context.utils.IntentBldr;
import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.construct.ISymbol;
import com.tregouet.context_interpreter.data_types.construct.impl.Construct;
import com.tregouet.context_interpreter.data_types.construct.impl.Terminal;
import com.tregouet.context_interpreter.data_types.construct.impl.Variable;
import com.tregouet.subseq_finder.ISubseqFinder;
import com.tregouet.subseq_finder.ISymbolSeq;
import com.tregouet.subseq_finder.impl.SubseqFinder;
import com.tregouet.subseq_finder.impl.SymbolSeq;

public class PosetOfCategories implements IPosetOfCategories {

	private final List<IContextObject> objects;
	private final Set<ICategory> lattice = new HashSet<ICategory>();
	private ICategory latticeMax;
	private final Set<ICategory> latticeAbstCat = new HashSet<ICategory>();
	protected final Set<ICategory> latticeObj = new HashSet<ICategory>();
	private ICategory latticeMin;
	private ICategory accept;
	private ICategory preAccept;
	private final Map<ICategory, Set<ICategory>> relation;
	private final Map<ICategory, Set<ICategory>> succRelation = new HashMap<ICategory, Set<ICategory>>();
	protected final Map<ICategory, Set<ICategory>> precRelation = new HashMap<ICategory, Set<ICategory>>();
	
	public PosetOfCategories(List<IContextObject> objects) {
		relation = new HashMap<ICategory, Set<ICategory>>();
		this.objects = objects;
		AVariable.initializeNameProvider();
		buildCategoryLatticeStrictPartialOrderRelation();
		instantiateAcceptCategory();
		instantiatePreAcceptCategory();
		addAcceptAndPreAcceptCatToRelation();
		buildSuccessorRelation();
		buildPredecessorRelation();
		updateCategoryRanks();
		nameVariables();
	}
	
	protected PosetOfCategories(List<IContextObject> objects, Map<ICategory, Set<ICategory>> relation) {
		this.objects = objects;
		for (ICategory cat : relation.keySet()) {
			switch (cat.type()) {
				case ICategory.LATT_OBJ :
					latticeObj.add(cat);
					lattice.add(cat);
					break;
				case ICategory.LATT_CAT : 
					latticeAbstCat.add(cat);
					lattice.add(cat);
					break;
				case ICategory.LATT_MAX :
					latticeMax = cat;
					lattice.add(cat);
					break;
				case ICategory.PREACCEPT :
					preAccept = cat;
					break;
				case ICategory.ACCEPT :
					accept = cat;
					break;
			}
		}
		this.relation = relation;
		buildSuccessorRelation();
		buildPredecessorRelation();
	}
	
	public int compare(ICategory cat1, ICategory cat2) {
		//HERE
		int hashCodeCat1 = cat1.hashCode();
		int hashCodeCat2 = cat2.hashCode();
		//
		if (relation.get(cat1).contains(cat2))
			return IPosetOfCategories.SUPER_CATEGORY;
		else if (relation.get(cat2).contains(cat1))
			return IPosetOfCategories.SUB_CATEGORY;
		else if (cat2.equals(cat1))
			return IPosetOfCategories.EQUALS;
		return IPosetOfCategories.UNCOMPARABLE;
	}

	public ICategory getAcceptCategory() {
		return accept;
	}
	
	public Set<ICategory> getCategories(){
		return relation.keySet();
	}
	
	public Map<ICategory, Set<ICategory>> getCategoryLatticeSuccRel() {
		Map<ICategory, Set<ICategory>> latticeSuccRel = 
				new HashMap<ICategory, Set<ICategory>>(succRelation);
		latticeSuccRel.remove(accept);
		latticeSuccRel.remove(preAccept);
		return latticeSuccRel;
	}

	public ICategory getCatLatticeMax() {
		return latticeMax;
	}

	public ICategory getCatLatticeMin() {
		return latticeMin;
	}
	
	@Override
	public Map<IConstruct, ICategory> getConstructToCategoryMap() {
		Set<ICategory> allCategoriesExceptMinimum = new HashSet<ICategory>(relation.keySet());
		allCategoriesExceptMinimum.remove(latticeMin);
		Map<IConstruct, ICategory> constructToCategory = new HashMap<IConstruct, ICategory>();
		for (ICategory category : allCategoriesExceptMinimum) {
			for (IConstruct construct : category.getIntent())
				constructToCategory.put(construct, category);
		}
		return constructToCategory;
	}

	public Set<ICategory> getLattice(){
		return lattice;
	}
	
	public Set<ICategory> getLatticeAbstCategories(){
		return latticeAbstCat;
	}
	
	@Override
	public Set<ICategory> getLowerBounds(ICategory category) {
		return relation.get(category);
	}
	
	public Set<ICategory> getObjectCategories() {
		return latticeObj;
	}	
	
	@Override
	public List<IContextObject> getObjects() {
		return objects;
	}	
	
	public ICategory getPreAcceptCategory() {
		return preAccept;
	}
	
	public Map<ICategory, Set<ICategory>> getPrecRelOverCategories() {
		return precRelation;
	}

	@Override
	public Set<ICategory> getPredecessors(ICategory category) {
		return precRelation.get(category);
	}

	public Map<ICategory, Set<ICategory>> getRelOverCategories() {
		return relation;
	}

	@Override
	public Set<List<ICategory>> getSpanningChains() {
		return continueSpanningChains(getAcceptCategory());
	}

	@Override
	public Set<ICategory> getSuccessors(ICategory category) {
		return succRelation.get(category);
	}
	
	

	public Map<ICategory, Set<ICategory>> getSuccRelOverCategories() {
		return succRelation;
	}

	@Override
	public Set<ICategory> getUpperBounds(ICategory category) {
		Set<ICategory> upperBounds = new HashSet<ICategory>();
		for (ICategory cat : relation.keySet()) {
			if (relation.get(cat).contains(category))
			upperBounds.add(category);
		}
		return upperBounds;
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

	private void buildPredecessorRelation() {
		for (ICategory cat : succRelation.keySet()) {
			precRelation.put(cat, new HashSet<ICategory>());
		}
		for (ICategory cat : succRelation.keySet()) {
			for (ICategory succCat : succRelation.get(cat))
				precRelation.get(succCat).add(cat);
		}
	}

	private void buildSuccessorRelation() {
		for (ICategory cat : relation.keySet())
			succRelation.put(cat, new HashSet<ICategory>(relation.get(cat)));
		for (ICategory cat : succRelation.keySet()) {
			for (ICategory otherCat : succRelation.keySet()) {
				if (!cat.equals(otherCat) && succRelation.get(otherCat).contains(cat)) {
					succRelation.get(otherCat).removeAll(succRelation.get(cat));
				}
			}
		}
	}

	//recursive
	private Set<List<ICategory>> continueSpanningChains(ICategory category){
		Set<List<ICategory>> chains = new HashSet<List<ICategory>>();
		if (!succRelation.get(category).isEmpty()) {
			Set<List<ICategory>> subchains = new HashSet<List<ICategory>>();
			for (ICategory succ : succRelation.get(category)) {
				//recursion
				subchains.addAll(continueSpanningChains(succ));
			}
			for (List<ICategory> subchain : subchains){
				List<ICategory> nextChain = new ArrayList<>(subchain);
				nextChain.add(0, category);
				chains.add(nextChain);
			}
		}
		else {
			List<ICategory> chain = new ArrayList<ICategory>();
			chain.add(category);
			chains.add(chain);
		}
		return chains;
	}
	
	private void instantiateAcceptCategory() {
		ISymbol variable = new Variable(AVariable.DEFERRED_NAMING);
		List<ISymbol> acceptProg = new ArrayList<ISymbol>();
		acceptProg.add(variable);
		IConstruct acceptConstruct = new Construct(acceptProg);
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
			lattMaxSymbolSeq.add(new SymbolSeq(construct.toListOfStrings()));
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
						preAccSymList.add(new Variable(Variable.DEFERRED_NAMING));
						lastSymStringWasPlaceholder = true;
					}
				}
				else {
					preAccSymList.add(new Terminal(symString));
					lastSymStringWasPlaceholder = false;
				}
			}
			preAccIntent.add(new Construct(preAccSymList));
		}
		preAccept = new Category(preAccIntent, new HashSet<IContextObject>(objects));
		preAccept.setType(ICategory.PREACCEPT);
	}

	private void nameVariables() {
		AVariable.initializeNameProvider();
		for (ICategory cat : relation.keySet()) {
			for (IConstruct construct : cat.getIntent()) {
				for (ISymbol symbol : construct.getListOfSymbols())
					if (symbol instanceof AVariable)
						((AVariable) symbol).setName();
			}
		}
	}

	private void updateCategoryRanks() {
		updateCategoryRanks(latticeMin, 0);
	}

	//recursive
	private void updateCategoryRanks(ICategory cat, int rank) {
		if (cat.rank() < rank || cat.type() == ICategory.LATT_MIN) {
			cat.setRank(rank);
			for (ICategory predecessor : precRelation.get(cat))
				updateCategoryRanks(predecessor, rank + 1);
		}
	}
}
