package com.tregouet.context_interpreter.context.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.context.IPosetOfConstructs;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.io.inputs.impl.GenericFileReader;

public class PosetOfConstructsTest {

	private static Path shapes3 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes3.txt");
	List<IContextObject> shapes3Obj;
	IPosetOfCategories catRel3;
	IPosetOfConstructs constRel3;
	
	@Before
	public void setUp() throws Exception {
		shapes3Obj = GenericFileReader.getContextObjects(shapes3);
		catRel3 = new PosetOfCategories(shapes3Obj);
		constRel3 = new PosetOfConstructs(catRel3);
	}
	
	@Test
	public void whenCategoryOfSpecifiedConstructRequestedThenExpectedReturned() {
		boolean expectedReturned = true;
		Set<ICategory> allNonMinimumCategories = catRel3.getCategories();
		allNonMinimumCategories.remove(catRel3.getLatticeMin());
		for (ICategory currCat : catRel3.getCategories()) {
			for (IConstruct currConst : currCat.getIntent()) {
				ICategory returned = constRel3.getCategoryOf(currConst);
				if (!returned.equals(currCat))
					expectedReturned = false;
			}
		}
		assertTrue(expectedReturned);
	}
	
	@Test
	public void whenConstructsComparedThenResultAsExpected() {
		boolean expected = true;
		List<IConstruct> constructs = new ArrayList<IConstruct>(constRel3.getRelation().keySet());
		for (int i = 0 ; i < constructs.size() ; i++) {
			for (int j = i ; j < constructs.size() ; j++) {
				IConstruct c1 = constructs.get(i);
				IConstruct c2 = constructs.get(j);
				ICategory cat1 = constRel3.getCategoryOf(c1);
				ICategory cat2 = constRel3.getCategoryOf(c2);
				int comparison = constRel3.compare(c1, c2);
				switch (comparison) {
					case IPosetOfConstructs.ABSTRACTION_OF : 
						if (IPosetOfCategories.compareStatic(cat1, cat2) != IPosetOfCategories.SUPER)
							expected = false;
						if (!IPosetOfConstructs.generates(c1, c2))
							expected = false;
						break;
					case IPosetOfConstructs.INSTANCE_OF :
						if (IPosetOfCategories.compareStatic(cat1, cat2) != IPosetOfCategories.SUB)
							expected = false;
						if (!IPosetOfConstructs.generates(c2, c1))
							expected = false;
						break;
					case IPosetOfConstructs.SAME_AS : 
						if (!cat1.equals(cat2) || !c1.equals(c2))
							expected = false;
						break;
					case IPosetOfConstructs.UNCOMPARABLE : 
						if ((IPosetOfConstructs.generates(c1, c2) 
								&& IPosetOfCategories.compareStatic(cat1, cat2) == IPosetOfCategories.SUPER) 
									|| (IPosetOfConstructs.generates(c2, c1) 
											&& IPosetOfCategories.compareStatic(cat2, cat1) == IPosetOfCategories.SUPER))
							expected = false;
						break;
					default : 
						expected = false;
				}
			}
		}
		assertTrue(expected);
	}
	
	@Test
	public void whenMaximumRequestedThen1Var0TermConstructReturned() {
		IConstruct max = constRel3.getMaximum();
		assertTrue(max.getListOfSymbols().size() == 1 && max.getNbOfTerminals() == 0);
	}
	
	@Test
	public void whenMinimaRequestedThenUnionOfObjIntentsReturned() {
		Set<IConstruct> minima = constRel3.getMinima();
		Set<IConstruct> unionOfObjIntents = new HashSet<IConstruct>();
		for (IContextObject obj : shapes3Obj)
			unionOfObjIntents.addAll(obj.getConstructs());
		/*
		 * Works because of a side effect : construct intents that are singuralized during the 
		 * instiantiation of the poset of constructs are the elements of the object's intent.   
		 */
		assertTrue(minima.equals(unionOfObjIntents));
	}
	
	@Test
	public void whenRequestedThenTransitionRelOverCatsYieldsOriginalSpanningChainsOfConstructsPlusNewOnes() {
		Set<List<IConstruct>> constructChainsFromOriginalRelOverCats = constRel3.getSpanningChains();
		//set construct chains from transition relation over categories
		Set<List<IConstruct>> constructChainsFromTransitionRelOverCats;
		//.set transition relation over categories
		Map<ICategory, Set<ICategory>> transitionRel = constRel3.getTransitionRelationOverCategories();
		//.set restricted relation over constructs (filtered by transition relation over categories)
		Map<IConstruct, Set<IConstruct>> transitionConstructRel = new HashMap<IConstruct, Set<IConstruct>>();
		for (IConstruct construct : constRel3.getRelation().keySet()) {
			transitionConstructRel.put(construct, new HashSet<IConstruct>());
		}
		for (ICategory cat : transitionRel.keySet()) {
			Set<IConstruct> catIntent = cat.getIntent();
			Set<IConstruct> subCatConstructs = new HashSet<IConstruct>();
			for (ICategory subCat : transitionRel.get(cat))
				subCatConstructs.addAll(subCat.getIntent());
			for (IConstruct construct : catIntent) {
				for (IConstruct subCatConstruct : subCatConstructs) {
					if (IPosetOfConstructs.generates(construct, subCatConstruct))
						transitionConstructRel.get(construct).add(subCatConstruct);
				}
			}
		}
		//.set spanning chains of constructs according to restricted relation
		constructChainsFromTransitionRelOverCats = continueChain(constRel3.getMaximum(), transitionConstructRel);
		assertTrue(constructChainsFromTransitionRelOverCats.containsAll(constructChainsFromOriginalRelOverCats)
				&& !constructChainsFromOriginalRelOverCats.containsAll(constructChainsFromTransitionRelOverCats));
	}
	
	@Test
	public void whenSpanningChainsrequestedThenEachConstructBelongsTo1ChainAtLeast() {
		boolean oneAtLeast = true;
		Map<IConstruct, Set<List<IConstruct>>> constructToChain 
			= new HashMap<IConstruct, Set<List<IConstruct>>>();
		for (IConstruct construct : constRel3.getRelation().keySet())
			constructToChain.put(construct, new HashSet<List<IConstruct>>());
		for (List<IConstruct> spanningChain : constRel3.getSpanningChains()){
			for (IConstruct chainElem : spanningChain) {
				constructToChain.get(chainElem).add(spanningChain);
			}
		}
		for (IConstruct construct1 : constructToChain.keySet()) {
			if (constructToChain.get(construct1).isEmpty())
				oneAtLeast = false;
			/*
			System.out.println(System.lineSeparator() + "NEW CONSTRUCT : " + construct1.toString());
			for (List<IConstruct> chain : constructToChain.get(construct1)) {
				System.out.println("***New Chain : ");
				for (IConstruct chainElem : chain)
					System.out.println(chainElem.toString());
			}
			*/
		}
		assertTrue(oneAtLeast);
	}
	
	@Test
	public void whenSpanningChainsRequestedThenReallySpanning() {
		boolean spanning = true;
		Set<List<IConstruct>> spanningChains = constRel3.getSpanningChains();
		/*
		int chainIdx = 1;
		System.out.println("**********SPANNING CHAINS**********");
		*/
		for (List<IConstruct> spanningChain : spanningChains) {
			/*
			System.out.println(System.lineSeparator() + "SPANNING CHAIN N°" + chainIdx++ + " : ");
			for (IConstruct construct : spanningChain)
				System.out.println(construct.toString());
			System.out.print(System.lineSeparator());
			*/
			if (!spanningChain.get(0).equals(constRel3.getMaximum()) 
					|| !constRel3.getMinima().contains(spanningChain.get(spanningChain.size() - 1)))
				spanning = false;
		}
		assertTrue(spanning);
	}
	
	@Test
	public void whenSuccessorRequestedThenReturnedIsAnInstanceOfParam() {
		boolean succAreInstances = true;
		for (IConstruct construct : constRel3.getRelation().keySet()) {
			for (IConstruct successor : constRel3.getSuccessors(construct)) {
				if (!IPosetOfConstructs.generates(construct, successor))
					succAreInstances = false;
			}
		}
		assertTrue(succAreInstances);
	}
	
	@Test
	public void whenTransitionRelOverCatsReturnedThenConstainsEveryCatExceptMinimum() {
		Set<ICategory> catsFromTransitionRel = constRel3.getTransitionRelationOverCategories().keySet();
		assertTrue(catsFromTransitionRel.equals(catRel3.getAllCategoriesExceptLatticeMinimum()));
	}	
	
	@Test
	public void whenTransitionRelOverCatsReturnedThenContainsSuccRelation() {
		//Except than lattice minimum is removed from transition relation
		boolean containsSuccRelation = true;
		Map<ICategory, Set<ICategory>> transitionRelation = constRel3.getTransitionRelationOverCategories();
		Map<ICategory, Set<ICategory>> succRelationOverCats = catRel3.getSuccRelOverCategories();
		Map<ICategory, Set<ICategory>> succRelationWithNoMin = new HashMap<ICategory, Set<ICategory>>();
		for (ICategory curr : succRelationOverCats.keySet()) {
			if (!curr.equals(catRel3.getLatticeMin())) {
				Set<ICategory> relatedExceptMin = new HashSet<ICategory>(succRelationOverCats.get(curr));
				relatedExceptMin.remove(catRel3.getLatticeMin());
				succRelationWithNoMin.put(curr, relatedExceptMin);
			}
		}
		for (ICategory cat : transitionRelation.keySet()) {
			/*
			System.out.println(System.lineSeparator() + "****NEW CATEGORY****");
			System.out.println("***transition relation subcategory hashcodes :");
			for (ICategory cat1 : transitionRelation.get(cat))
				System.out.println(cat1.hashCode());
			System.out.println("***successor relation subcategory hashcodes :");
			for (ICategory cat2 : succRelationWithNoMin.get(cat))
				System.out.println(cat2.hashCode());
				*/
			if (!transitionRelation.get(cat).containsAll(succRelationWithNoMin.get(cat)))
				containsSuccRelation = false;
		}
		assertTrue(containsSuccRelation);
	}
	
	@Test
	public void whenTransitionRelOverCatsReturnedThenSubRelationOfOriginalRelation() {
		boolean subRelationOfOriginalPoset = true;
		Map<ICategory, Set<ICategory>> relationOverCats = catRel3.getRelOverCategories();
		Map<ICategory, Set<ICategory>> transitionRelationOverCats 
			= constRel3.getTransitionRelationOverCategories();
		for(ICategory cat : transitionRelationOverCats.keySet()) {
			/*
			System.out.println(System.lineSeparator() + "****NEW CATEGORY****");
			System.out.println("***original relation subcategory hashcodes :");
			for (ICategory cat1 : relationOverCats.get(cat))
				System.out.println(cat1.hashCode());
			System.out.println("***transition relation relation subcategory hashcodes :");
			for (ICategory cat2 : transitionRelationOverCats.get(cat))
				System.out.println(cat2.hashCode());
			*/
			if (!relationOverCats.get(cat).containsAll(transitionRelationOverCats.get(cat)))
				subRelationOfOriginalPoset = false;				
		}
		assertTrue(subRelationOfOriginalPoset);
	}
	
	private Set<List<IConstruct>> continueChain(
			IConstruct paramConstruct, Map<IConstruct, Set<IConstruct>> transitionConstructRelation){
		Set<List<IConstruct>> returnedChains = new HashSet<List<IConstruct>>();
		Set<List<IConstruct>> continuedChains = new HashSet<List<IConstruct>>();
		Set<IConstruct> paramSubConstructs = transitionConstructRelation.get(paramConstruct);
		if (!paramSubConstructs.isEmpty()) {
			for (IConstruct paramSubConstruct : paramSubConstructs){
				returnedChains.addAll(continueChain(paramSubConstruct, transitionConstructRelation));
			}
			for (List<IConstruct> returnedChain : returnedChains) {
				List<IConstruct> continuedChain = new ArrayList<IConstruct>();
				continuedChain.add(paramConstruct);
				continuedChain.addAll(returnedChain);
				continuedChains.add(continuedChain);
			}
		}
		else {
			List<IConstruct> newChain = new ArrayList<>();
			newChain.add(paramConstruct);
			continuedChains.add(newChain);
		}
		return continuedChains;
	}
	
}
