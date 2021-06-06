package com.tregouet.context_interpreter.context.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.context.IPosetOfConstructs;
import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.construct.ISymbol;
import com.tregouet.context_interpreter.data_types.construct.impl.AbstractConstruct;
import com.tregouet.context_interpreter.io.inputs.impl.GenericFileReader;
import com.tregouet.context_interpreter.io.outputs.exceptions.VisualizationException;

@SuppressWarnings("unused")
public class PosetOfCategoriesTest {

	private static Path shapes3 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes3.txt");
	List<IContextObject> shapes3Obj;
	IPosetOfCategories catRel3;
	
	@SuppressWarnings("unused")
	@Before
	public void setUp() throws Exception {
		shapes3Obj = GenericFileReader.getContextObjects(shapes3);
		catRel3 = new PosetOfCategories(shapes3Obj);
	}

	@Test
	public void whenCategoriesReturnedThenContains1Accept1PreacceptCatAnd1LatticeMaxCat() {
		int nbOfAcceptCat = 0;
		int nbOfPreAcceptCat = 0;
		int nbOfLatticeMaxCat = 0;
		for (ICategory cat : catRel3.getCategories()) {
			if (cat.type() == ICategory.ACCEPT)
				nbOfAcceptCat++;
			else if (cat.type() == ICategory.PREACCEPT)
				nbOfPreAcceptCat++;
			else if (cat.type() == ICategory.LATT_MAX)
				nbOfLatticeMaxCat++;
		}
		assertTrue ((nbOfAcceptCat == 1) && (nbOfPreAcceptCat == 1) && (nbOfLatticeMaxCat == 1));
	}
	
	@Test
	public void whenCategoriesReturnedThenEachHasDistinctIntent() {
		boolean allIntentsAreDistinct = true;
		List<ICategory> categories = new ArrayList<ICategory>(catRel3.getCategories());
		for (int i = 0 ; i < categories.size() ; i++) {
			for (int j = i+1 ; j < categories.size() ; j++) {
				if (categories.get(i).getIntent().equals(categories.get(j).getIntent()))
					allIntentsAreDistinct  = false;
			}
		}
		assertTrue(allIntentsAreDistinct);
	}
	
	@Test
	public void whenCategoriesReturnedThenLatticeMaxCatPreAcceptCatAndAcceptCatHaveSameExtent() {
		boolean sameExtent;
		ICategory accept = catRel3.getAcceptCategory();
		ICategory preAccept = catRel3.getPreAcceptCategory();
		ICategory latticeMax = catRel3.getCatLatticeMax();
		sameExtent = (accept.getExtent().equals(preAccept.getExtent()) 
				&& accept.getExtent().equals(latticeMax.getExtent()));
		assertTrue(sameExtent);
	}
	
	@Test
	public void whenCategoriesReturnedThenObjectCategoriesAtRank1() {
		boolean objCatsAtRAnk1 = true;
		Set<ICategory> categories = catRel3.getCategories();
		for (ICategory cat : categories) {
			if (cat.rank() == 1) {
				if (cat.type() != ICategory.LATT_OBJ)
					objCatsAtRAnk1 = false;
			}
			else if (cat.type() == ICategory.LATT_OBJ)
				objCatsAtRAnk1 = false;
		}
		assertTrue(objCatsAtRAnk1);		
	}
	
	@Test
	public void whenLatticeCategoriesReturnedThenEachHasDistinctExtent() {
		boolean allExtentsAreDistinct = true;
		List<ICategory> categories = new ArrayList<ICategory>(catRel3.getLatticeAbstCategories());
		for (int i = 0 ; i < categories.size() ; i++) {
			for (int j = i+1 ; j < categories.size() ; j++) {
				if (categories.get(i).getExtent().equals(categories.get(j).getExtent()))
					allExtentsAreDistinct  = false;
			}
		}
		assertTrue(allExtentsAreDistinct);
	}	
	
	@Test
	public void whenLatticeReturnedThenReallyIsALattice() {
		/*
		 * (except that an order relation has to be reflexive for an ordered set to 
		 * be a lattice - but apart from that...) 
		 */
		boolean eachCatPairHasASupremum = true;
		boolean eachCatPairHasAnInfimum = true;
		for (ICategory cat1 : catRel3.getLattice()) {
			for (ICategory cat2 : catRel3.getLattice()) {
				Set<ICategory> cat1SubCat = catRel3.getRelOverCategories().get(cat1);
				Set<ICategory> cat2SubCat = catRel3.getRelOverCategories().get(cat2);
				/*
				 * since the relation is not implemented as a reflexive relation,
				 * the 3 cases below wouldn't yield the result expected for a lattice 
				*/
				if (!cat1.equals(cat2) && !cat1SubCat.contains(cat2) && !cat2SubCat.contains(cat1)) {
					//build common lower bounds set & common upper bounds set
					Set<ICategory> commonLowerBounds = new HashSet<ICategory>();
					commonLowerBounds.addAll(catRel3.getRelOverCategories().get(cat1));
					commonLowerBounds.retainAll(catRel3.getRelOverCategories().get(cat2));
					Set<ICategory> commonUpperBounds = new HashSet<ICategory>();
					for (ICategory other : catRel3.getLattice()) {
						if (catRel3.getRelOverCategories().get(other).contains(cat1)
								&& catRel3.getRelOverCategories().get(other).contains(cat2))
							commonUpperBounds.add(other);
					}
					//build greatest lower bounds set & least upper bounds set
					Set<ICategory> greatestLowerBounds = new HashSet<ICategory>(commonLowerBounds);
					Set<ICategory> leastUpperBounds = new HashSet<ICategory>(commonUpperBounds);
					for (ICategory lowerBound : commonLowerBounds) {
						for (ICategory otherLowerBound : commonLowerBounds) {
							if (catRel3.getRelOverCategories().get(lowerBound)
									.contains(otherLowerBound))
								greatestLowerBounds.remove(otherLowerBound);
						}
					}
					for (ICategory upperBound : commonUpperBounds) {
						for (ICategory otherUpperBound : commonUpperBounds) {
							if (catRel3.getRelOverCategories().get(upperBound)
									.contains(otherUpperBound))
								leastUpperBounds.remove(upperBound);
						}
					}
					//test
					if (greatestLowerBounds.size() != 1)
						eachCatPairHasAnInfimum = false;
					if (leastUpperBounds.size() != 1)
						eachCatPairHasASupremum = false;
				}
			}
		}
		assertTrue(eachCatPairHasAnInfimum && eachCatPairHasASupremum);
	}
	
	@Test
	public void whenTransitionRelationReturnedThenIsASubsetOfOriginalRelationOverCats() {
		boolean isASubset = true;
		Map<ICategory, Set<ICategory>> relation = catRel3.getRelOverCategories();
		Map<ICategory, Set<ICategory>> transitionRelation = catRel3.getTransitionRelationOverCategories();
		for (ICategory cat : transitionRelation.keySet()) {
			if (!relation.containsKey(cat))
				isASubset = false;
			else if (!relation.get(cat).containsAll(transitionRelation.get(cat)))
				isASubset = false;
			/*
			int diff = relation.get(cat).size() - transitionRelation.get(cat).size();
			System.out.println("original / transition relation diff : " + diff);
			*/
		}
		assertTrue(isASubset);
	}
	
	@Test
	public void whenTransitionRelationReturnedThenContainsRetrictedSuccessorRelationOverCats() {
		//Successor relation is restricted to the set of all categories but the minimum
		boolean containsSuccRelation = true;
		Map<ICategory, Set<ICategory>> succRelation = catRel3.getSuccRelOverCategories();
		//set restriction
		Map<ICategory, Set<ICategory>> restrictedSuccRelation = new HashMap<ICategory, Set<ICategory>>();
		for (Entry<ICategory, Set<ICategory>> succEntry : succRelation.entrySet()) {
			if (!succEntry.getKey().equals(catRel3.getCatLatticeMin())) {
				Set<ICategory> relatedMinusMin = new HashSet<ICategory>(succEntry.getValue());
				relatedMinusMin.remove(catRel3.getCatLatticeMin());
				restrictedSuccRelation.put(succEntry.getKey(), relatedMinusMin);
			}
		}
		//test
		Map<ICategory, Set<ICategory>> transitionRelation = catRel3.getTransitionRelationOverCategories();
		for (ICategory cat : restrictedSuccRelation.keySet()) {
			if (!transitionRelation.containsKey(cat))
				containsSuccRelation = false;
			else if (!transitionRelation.get(cat).containsAll(restrictedSuccRelation.get(cat)))
				containsSuccRelation = false;
			/*
			int diff = transitionRelation.get(cat).size() - restrictedSuccRelation.get(cat).size(); 
			if (diff != 0) {
				System.out.println("category of interest : ");
				for (IConstruct catConstr : cat.getIntent()) {
					System.out.println(catConstr.toString());
				}
				System.out.println(System.lineSeparator() + "lattice max intent : ");
				for (IConstruct maxConstr : catRel3.getCatLatticeMax().getIntent()) {
					System.out.println(maxConstr.toString());
				}
				System.out.println(System.lineSeparator() + "transition / succ relation diff : " + diff);
				System.out.print(System.lineSeparator() + "****Related in transition relation :");
				for (ICategory transRelated : transitionRelation.get(cat)) {
					System.out.println(System.lineSeparator() + "*New related category : ");
					for (IConstruct transRelatedConstr : transRelated.getIntent())
						System.out.println(transRelatedConstr.toString());
				}
				System.out.println(System.lineSeparator() + "****Related in successor relation :");
				for (ICategory succRelated : restrictedSuccRelation.get(cat)) {
					System.out.println(System.lineSeparator() + "*New related category : ");
					for (IConstruct succRelatedConstr : succRelated.getIntent())
						System.out.println(succRelatedConstr.toString());
				}
			}
			*/
		}
		assertTrue(containsSuccRelation);
	}
	
	@Test
	public void whenGraphBuildingRequestedThenDone() throws VisualizationException {
		assertTrue(catRel3.buildSuccessorRelationGraph());
	}
	
	
	private void printCategories(IPosetOfCategories catRel) {
		int maxRank = catRel.getAcceptCategory().rank();
		System.out.println("**********NEW SET OF CATEGORIES**********" + System.lineSeparator());
		for (int i = 0 ; i <= maxRank ; i++) {
			System.out.println("***** Categories : rank " + i + " *****" + System.lineSeparator());
			for (ICategory cat : catRel.getPrecRelOverCategories().keySet()) {
				if (cat.rank() == i) {
					System.out.println("*** New category ***");
					System.out.println("**Intent : ");
					for (IConstruct construct : cat.getIntent()) {
						System.out.println(construct.toString());
					}
					System.out.println("**Extent : ");
					for (IContextObject obj : cat.getExtent())
						System.out.print(obj.getID() + " ; ");
					System.out.println(System.lineSeparator());
				}
			}
		}
	}
	


}
