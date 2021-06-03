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
import com.tregouet.context_interpreter.inputs.impl.GenericFileReader;

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
						if (IPosetOfCategories.compareStatic(cat1, cat2) != IPosetOfCategories.SUPER_CATEGORY)
							expected = false;
						if (!IPosetOfConstructs.generates(c1, c2))
							expected = false;
						break;
					case IPosetOfConstructs.INSTANCE_OF :
						if (IPosetOfCategories.compareStatic(cat1, cat2) != IPosetOfCategories.SUB_CATEGORY)
							expected = false;
						if (!IPosetOfConstructs.generates(c2, c1))
							expected = false;
						break;
					case IPosetOfConstructs.SAME_AS : 
						//no use since it should never happen
						if (!cat1.equals(cat2) || !c1.equals(c2))
							expected = false;
						break;
					case IPosetOfConstructs.UNCOMPARABLE : 
						if ((IPosetOfConstructs.generates(c1, c2) 
								&& IPosetOfCategories.compareStatic(cat1, cat2) == IPosetOfCategories.SUPER_CATEGORY) 
									|| (IPosetOfConstructs.generates(c2, c1) 
											&& IPosetOfCategories.compareStatic(cat2, cat1) == IPosetOfCategories.SUPER_CATEGORY))
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
	public void whenCategoryOfSpecifiedConstructRequestedThenExpectedReturned() {
		boolean expectedReturned = true;
		Set<ICategory> allNonMinimumCategories = catRel3.getCategories();
		allNonMinimumCategories.remove(catRel3.getCatLatticeMin());
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
	public void whenSpanningChainsRequestedThenReallySpanning() {
		boolean spanning = true;
		Set<List<IConstruct>> spanningChains = constRel3.getSpanningChains();
		for (List<IConstruct> spanningChain : spanningChains) {
			if (!spanningChain.get(0).equals(constRel3.getMaximum()) 
					|| !constRel3.getMinima().contains(spanningChain.get(spanningChain.size() - 1)))
				spanning = false;
		}
		assertTrue(spanning);
	}
	
	@Test
	public void whenSpanningChainsrequestedThenOneOreMoreChainForEachLeaf() {
		boolean oneOreMore = true;
		Map<IConstruct, Set<List<IConstruct>>> minToChain = new HashMap<IConstruct, Set<List<IConstruct>>>();
		for (IConstruct minimal : constRel3.getMinima())
			minToChain.put(minimal, new HashSet<List<IConstruct>>());
		for (List<IConstruct> spanningChain : constRel3.getSpanningChains())
			minToChain.get(spanningChain.get(spanningChain.size() - 1)).add(spanningChain);
		for (IConstruct minimal : minToChain.keySet()) {
			if (minToChain.get(minimal).isEmpty())
				oneOreMore = false;
		}
		assertTrue(oneOreMore);
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
	public void whenFilteredPosetRequestedThenReturned() {
		IPosetOfCategories filteredPoset = constRel3.getFilteredPosetOfCategories();
		assertTrue(true);
	}
	
}
