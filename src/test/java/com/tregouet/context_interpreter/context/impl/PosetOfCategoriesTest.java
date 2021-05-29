package com.tregouet.context_interpreter.context.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.construct.ISymbol;
import com.tregouet.context_interpreter.data_types.construct.impl.Construct;
import com.tregouet.context_interpreter.inputs.impl.GenericFileReader;

@SuppressWarnings("unused")
public class PosetOfCategoriesTest {

	private static Path shapes3 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes3.txt");
	List<IContextObject> shapes3Obj;
	IPosetOfCategories catRel3;
	
	@SuppressWarnings("unused")
	@Before
	public void setUp() throws Exception {
		shapes3Obj = GenericFileReader.getContextObjects(shapes3);
		//catRel2 = new CatRelationBldr(shapes2Obj);
		//printCategories(catRel2);
		catRel3 = new PosetOfCategories(shapes3Obj);
	}

	@Test
	public void whenRelationReturnedThenEachCategoryHasDistinctIntent() {
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
	public void whenRelationReturnedThenEachLatticeCategoryHasDistinctExtent() {
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
	public void whenRelationReturnedThenObjectCategoriesAtRank1() {
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
	public void whenRelationReturnedThenContains1AcceptAnd1PreacceptCat() {
		int nbOfAcceptCat = 0;
		int nbOfPreAcceptCat = 0;
		for (ICategory cat : catRel3.getCategories()) {
			if (cat.type() == ICategory.ACCEPT)
				nbOfAcceptCat++;
			else if (cat.type() == ICategory.PREACCEPT)
				nbOfPreAcceptCat++;
		}
		assertTrue ((nbOfAcceptCat == 1) && (nbOfPreAcceptCat == 1));
	}	
	
	@Test
	public void whenLatticeReturnedThenReallyIsALattice() {
		/*
		 * (except that an order relation has to be reflexive for an ordered set to 
		 * be a lattice - but apart from that...) (apologies to mathematicians)   
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
	public void whenRelationReturnedThenCatIntentsCanBeGeneratedFromAnySuperCatIntents() {
		/*
		 * If A is a supercategory of B, then for any construct B.y in B's intent, there exists 
		 * at least one construct A.i in A's intent such that B.y can be generated by the 
		 * assignment of values to A.i variables. 
		 * 
		 * Exception : The lattice minimal category's intent is the union of all object 
		 * category's (or atom's) intents, whose constructs contain no variable.
		 */
		boolean allSubCatConstructsCanBeGenerated = true;
		for (ICategory superCategory : catRel3.getCategories()) {
			//HERE
			System.out.println("*****SUPER CATEGORY*****");
			System.out.println(System.lineSeparator());
			System.out.println("***SuperCat extent : ");
			for (IContextObject object : superCategory.getExtent()) {
				System.out.println("object ID : " + object.getID());
				System.out.println("object constructs : ");
				for (IConstruct construct : object.getConstructs()) {
					System.out.println(construct.toString());
				}
				System.out.println(System.lineSeparator());
			}
			System.out.println("***SuperCat intent");
			for (IConstruct construct : superCategory.getIntent())
				System.out.println(construct.toString());
			System.out.println(System.lineSeparator());
			//HERE
			for (ICategory subCategory : catRel3.getCategories()) {
				if (catRel3.getRelOverCategories().get(superCategory).contains(subCategory)
						&& subCategory.type() != ICategory.LATT_MIN) {
					//then subCategory really is a subcategory
					//HERE
					System.out.println("*****SUB CATEGORY*****");
					System.out.println(System.lineSeparator());
					System.out.println("***subCat intent :");
					for (IConstruct construct : subCategory.getIntent())
						System.out.println(construct.toString());
					System.out.println(System.lineSeparator());
					System.out.println("***subCat extent :");
					for (IContextObject obj : subCategory.getExtent())
						System.out.print(obj.getID() + " ;" );
					System.out.println(System.lineSeparator() + System.lineSeparator());
					//HERE
					Set<IConstruct> superCatIntent = superCategory.getIntent();
					Set<IConstruct> subCatIntent = subCategory.getIntent();
					for (IConstruct subCatConstruct : subCatIntent) {
						//needs to be 'true' at the end of the loop
						boolean subCatIntentGeneratedFromSuper = false;
						for (IConstruct superCatConstruct : superCatIntent) {
							
							List<ISymbol> superCatConstrList = superCatConstruct.getListOfSymbols();
							List<ISymbol> subCatConstrList = subCatConstruct.getListOfSymbols();
							int superIdx = 0;
							for (int subIdx = 0 ; 
									(subIdx < subCatConstrList.size()) 
									&& (superIdx < superCatConstrList.size()) ;	subIdx++) {
								if (subCatConstrList.get(subIdx)
										.equals(superCatConstrList.get(superIdx))) {
									superIdx++;
									if ((superIdx < superCatConstrList.size()) 
											&& (superCatConstrList.get(superIdx) instanceof AVariable))
										//there can't be 2 consecutive variables
										superIdx++;
								}
							}
							if (superIdx == superCatConstrList.size())
								/*
								 * Then current subcategory construct can be generated by 
								 * instantiating current supercategory's construct. 
								 */
								subCatIntentGeneratedFromSuper = true;
						}
						if (!subCatIntentGeneratedFromSuper)
							//then at least one counter-example
							allSubCatConstructsCanBeGenerated = false;
					}
				}
			}
		}
		assertTrue(allSubCatConstructsCanBeGenerated);
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
