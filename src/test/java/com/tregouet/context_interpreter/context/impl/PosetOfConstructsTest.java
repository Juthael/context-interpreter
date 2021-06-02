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
						if (IPosetOfCategories.compareStatic(cat1, cat2) != IPosetOfCategories.UNCOMPARABLE)
							expected = false;
						if (IPosetOfConstructs.generates(c1, c2) || IPosetOfConstructs.generates(c2, c1))
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
		//HERE
		for (IConstruct construct : constRel3.getRelation().keySet()) {
			if (constRel3.getLowerBounds(construct).isEmpty()) {
				if (construct.getNbOfTerminals() < construct.getListOfSymbols().size()) {
					System.out.println("PROBLEMATIC CONSTRUCT : ");
					System.out.println(construct.toString() + System.lineSeparator());
					System.out.println("cat hashcode : " + constRel3.getCategoryOf(construct).hashCode());
					System.out.println("cat type :" + constRel3.getCategoryOf(construct).type());
					System.out.println("cat rank :" + constRel3.getCategoryOf(construct).rank());
					System.out.println("cat nb of subCategories : " + 						catRel3.getSuccessors(constRel3.getCategoryOf(construct)).size());
					System.out.println("cat intent : ");
					for (IConstruct catConst : constRel3.getCategoryOf(construct).getIntent()) {
						System.out.println(catConst.toString());
					}
					System.out.println("cat extent : ");
					for (IContextObject obj : constRel3.getCategoryOf(construct).getExtent()) {
						System.out.println(obj.getID() + " ; ");
					}
					System.out.println(System.lineSeparator());
					int subCatIdx = 1;
					for (ICategory subCat : catRel3.getSuccessors(constRel3.getCategoryOf(construct))) {
						System.out.println("SUBCAT N° " + subCatIdx + " : ");
						System.out.println("subcat hashcode : " + subCat.hashCode());
						for (IConstruct subCatConst : subCat.getIntent()) {
							System.out.println(subCatConst.toString());
						}
						System.out.println("cat extent : ");
						for (IContextObject subCatObj : subCat.getExtent()) {
							System.out.println(subCatObj.getID() + " ; ");
						}
						for (IConstruct subCatConst : subCat.getIntent()) {
							boolean subCatConstIsAnInstance = IPosetOfConstructs.generates(construct, subCatConst);
							System.out.println(subCatConst.toString() + " -> " + subCatConstIsAnInstance);
							if (subCatConstIsAnInstance) {
								if(constRel3.compare(construct, subCatConst) == IPosetOfConstructs.ABSTRACTION_OF)
									System.out.println("Recognized as such");
								else System.out.println("Not recognized");
							}
							
						}
						subCatIdx++;
						System.out.println(System.lineSeparator());
					}
				}
				System.out.println("******************" + System.lineSeparator());
			}
				
		}
		//HERE
		//HERE
		for (IConstruct construct : constRel3.getRelation().keySet()) {
			System.out.println("NEW CONSTRUCT : " + System.lineSeparator());
			System.out.println(construct.toString());
			System.out.println("successors : ");
			for (IConstruct succ : constRel3.getSuccessors(construct))
				System.out.println(succ.toString());
			System.out.println(System.lineSeparator());
		}
		Set<IConstruct> minima = constRel3.getMinima();
		//HERE
		System.out.println("MINIMA");
		for (IConstruct min : minima)
			System.out.println(min.toString());
		System.out.println(System.lineSeparator());
		//
		Set<IConstruct> unionOfObjIntents = new HashSet<IConstruct>();
		for (IContextObject obj : shapes3Obj)
			unionOfObjIntents.addAll(obj.getConstructs());
		//HERE
		System.out.println("OBJ INTENTS : ");
		for (IConstruct constr : unionOfObjIntents)
			System.out.println(constr.toString());
		//
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
