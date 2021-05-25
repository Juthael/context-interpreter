package com.tregouet.context_interpreter.context.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.ICatRelationBldr;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.inputs.impl.GenericFileReader;

@SuppressWarnings("unused")
public class CatRelationBldrTest {

	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes2.txt");
	private static Path shapes3 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes3.txt");
	List<IContextObject> shapes2Obj;
	List<IContextObject> shapes3Obj;
	ICatRelationBldr catRel2;
	
	@SuppressWarnings("unused")
	@Before
	public void setUp() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		shapes3Obj = GenericFileReader.getContextObjects(shapes3);
		catRel2 = new CatRelationBldr(shapes2Obj);
		//printCategories(catRel2);
	}

	@Test
	public void whenRelationReturnedThenEachCategoryHasDistinctIntent() {
		boolean allIntentsAreDistinct = true;
		List<ICategory> categories = new ArrayList<ICategory>(catRel2.getCategories());
		for (int i = 0 ; i < categories.size() ; i++) {
			for (int j = i+1 ; j < categories.size() ; j++) {
				if (categories.get(i).getIntent().equals(categories.get(j).getIntent()))
					allIntentsAreDistinct  = false;
			}
		}
		assertTrue(allIntentsAreDistinct);
	}
	
	@Test
	public void whenRelationReturnedThenEachCategoryHasDistinctExtent() {
		boolean allExtentsAreDistinct = true;
		List<ICategory> categories = new ArrayList<ICategory>(catRel2.getCategories());
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
		Set<ICategory> categories = catRel2.getCategories();
		for (ICategory cat : categories) {
			if (cat.getRank() == 1) {
				if (cat.getType() != ICategory.LATT_OBJ)
					objCatsAtRAnk1 = false;
			}
			else if (cat.getType() == ICategory.LATT_OBJ)
				objCatsAtRAnk1 = false;
		}
		assertTrue(objCatsAtRAnk1);		
	}
	
	@Test
	public void whenRelationReturnedThenContains1AcceptAnd1PreacceptCat() {
		int nbOfAcceptCat = 0;
		int nbOfPreAcceptCat = 0;
		for (ICategory cat : catRel2.getCategories()) {
			if (cat.getType() == ICategory.ACCEPT)
				nbOfAcceptCat++;
			else if (cat.getType() == ICategory.PREACCEPT)
				nbOfPreAcceptCat++;
		}
		assertTrue ((nbOfAcceptCat == 1) && (nbOfPreAcceptCat == 1));
	}	
	
	@Test
	public void whenLatticeReturnedThenReallyIsALattice() {
		boolean only1UpperBoundForEachCatPair = true;
		boolean only1LowerBoundForEachCatPair = true;
		Map<ICategory, Set<ICategory>> precRelation = catRel2.getPrecRelOverCategories();
		Map<ICategory, Set<ICategory>> succRelation = catRel2.getSuccRelOverCategories();
		//HERE
	}
	
	@Test
	public void whenLargeSetInputThenRelationBuiltInReasonableTime() {
		fail("Not yet implemented");
	}
	
	
	
	private void printCategories(ICatRelationBldr catRel) {
		int maxRank = catRel.getAcceptCategory().getRank();
		System.out.println("**********NEW SET OF CATEGORIES**********" + System.lineSeparator());
		for (int i = 0 ; i <= maxRank ; i++) {
			System.out.println("***** Categories : rank " + i + " *****" + System.lineSeparator());
			for (ICategory cat : catRel.getPrecRelOverCategories().keySet()) {
				if (cat.getRank() == i) {
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
