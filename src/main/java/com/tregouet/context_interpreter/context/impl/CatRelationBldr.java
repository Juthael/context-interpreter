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
import com.tregouet.context_interpreter.context.ICatRelationBldr;
import com.tregouet.context_interpreter.context.utils.IntentBldr;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.construct.ISymbol;
import com.tregouet.context_interpreter.data_types.construct.impl.Construct;
import com.tregouet.context_interpreter.data_types.construct.impl.Variable;
import com.tregouet.subseq_finder.ISubseqFinder;
import com.tregouet.subseq_finder.ISymbolSeq;
import com.tregouet.subseq_finder.impl.SubseqFinder;
import com.tregouet.subseq_finder.impl.SymbolSeq;

public class CatRelationBldr implements ICatRelationBldr {

	private final List<IContextObject> objects;
	private final Set<ICategory> latticeElmnts = new HashSet<ICategory>();
	private ICategory latticeMax;
	private final Set<ICategory> latticeCat = new HashSet<ICategory>();
	private final Set<ICategory> latticeObj = new HashSet<ICategory>();
	private ICategory latticeMin;
	private final ICategory accept = buildAcceptCategory();
	private final ICategory preAccept = buildPreAcceptCategory();
	private final Map<ICategory, Set<ICategory>> succRelOverCategories = new HashMap<ICategory, Set<ICategory>>();
	
	public CatRelationBldr(List<IContextObject> objects) {
		this.objects = objects;
		buildCategoryLatticeStrictPartialOrderRelation();
		addAcceptAndPreAcceptCatToRelation();
		setRelationAsSuccessorRelation();	
	}

	private void addAcceptAndPreAcceptCatToRelation() {
		Set<ICategory> preAcceptSubCat = new HashSet<ICategory>(latticeElmnts);
		Set<ICategory> acceptSubCat = new HashSet<ICategory>(latticeElmnts);
		acceptSubCat.add(preAccept);
		succRelOverCategories.put(preAccept, preAcceptSubCat);
		succRelOverCategories.put(accept, acceptSubCat);
	}

	private ICategory buildAcceptCategory() {
		ICategory accept;
		ISymbol variable = new Variable(Variable.DEFERRED_NAMING);
		List<ISymbol> acceptProg = new ArrayList<ISymbol>();
		acceptProg.add(variable);
		IConstruct acceptConstruct = new Construct(acceptProg);
		Set<IConstruct> acceptIntent =  new HashSet<IConstruct>();
		acceptIntent.add(acceptConstruct);
		accept = new Category(acceptIntent, new HashSet<IContextObject>(objects));
		accept.setType(ICategory.ACCEPT);
		return accept;
	}

	private void buildCategoryLatticeStrictPartialOrderRelation() {
		Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents = setIntentToExtent();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : intentsToExtents.entrySet()) {
			ICategory category = new Category(entry.getKey(), entry.getValue());
			latticeElmnts.add(category);
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
				latticeCat.add(category);
			}
			succRelOverCategories.put(category, new HashSet<ICategory>());
		}
		List<ICategory> catList = new ArrayList<ICategory>(latticeElmnts);
		for (int i = 0 ; i < catList.size() ; i++) {
			for (int j = i+1 ; j < catList.size() ; j++) {
				if (catList.get(i).getExtent().containsAll(catList.get(j).getExtent()))
					succRelOverCategories.get(catList.get(i)).add(catList.get(j));
				else if (catList.get(j).getExtent().containsAll(catList.get(i).getExtent()))
					succRelOverCategories.get(catList.get(j)).add(catList.get(i));
			}
		}
	}

	private ICategory buildPreAcceptCategory() {
		Set<IConstruct> preAccIntent = new HashSet<IConstruct>();
		Set<IConstruct> lattMaxIntent = latticeMax.getIntent();
		List<ISymbolSeq> lattMaxSymbolSeq = new ArrayList<ISymbolSeq>();
		for (IConstruct construct : lattMaxIntent) {
			lattMaxSymbolSeq.add(new SymbolSeq(construct.toListOfStrings()));
		}
		Set<ISymbolSeq> maxCommonSubsqs;
		ISubseqFinder subseqFinder = new SubseqFinder(lattMaxSymbolSeq);
		maxCommonSubsqs = subseqFinder.getMaxCommonSubseqs();
		for (ISymbolSeq subsq : maxCommonSubsqs) {
			preAccIntent.add(new Construct(subsq));
		}
		return new Category(preAccIntent, new HashSet<IContextObject>(objects));
	}
	
	public ICategory getAcceptCategory() {
		return accept;
	}	
	
	public Map<ICategory, Set<ICategory>> getCategoryLatticeSuccRel() {
		Map<ICategory, Set<ICategory>> latticeSuccRel = 
				new HashMap<ICategory, Set<ICategory>>(succRelOverCategories);
		latticeSuccRel.remove(accept);
		latticeSuccRel.remove(preAccept);
		return latticeSuccRel;
	}
	
	public ICategory getCatLatticeMax() {
		return latticeMax;
	}

	public ICategory getLatticeMin() {
		return latticeMin;
	}

	public Set<ICategory> getObjectCategories() {
		return latticeObj;
	}

	public ICategory getPreAcceptCategory() {
		return preAccept;
	}

	public Map<ICategory, Set<ICategory>> getSuccRelOverCategories() {
		return succRelOverCategories;
	}

	//public for test use only
	public Map<Set<IConstruct>, Set<IContextObject>> setIntentToExtent() {
		Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents = 
				new HashMap<Set<IConstruct>, Set<IContextObject>>();
		Set<Set<IContextObject>> objectsPowerSet = setObjectsPowerSet();
		for (Set<IContextObject> subset : objectsPowerSet) {
			Set<IConstruct> intent = IntentBldr.getIntent(subset);
			if (intentsToExtents.containsKey(intent))
				intentsToExtents.get(subset).addAll(subset);
			else intentsToExtents.put(intent, subset);
		}
		return intentsToExtents;
	}

	private Set<Set<IContextObject>> setObjectsPowerSet() {
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

	private void setRelationAsSuccessorRelation() {
		for (ICategory cat : succRelOverCategories.keySet()) {
			for (ICategory otherCat : succRelOverCategories.keySet()) {
				if (!cat.equals(otherCat) && succRelOverCategories.get(otherCat).contains(cat)) {
					succRelOverCategories.get(otherCat).removeAll(succRelOverCategories.get(cat));
				}
			}
		}
	}

}
