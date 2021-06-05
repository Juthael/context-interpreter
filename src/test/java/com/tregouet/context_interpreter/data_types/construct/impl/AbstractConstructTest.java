package com.tregouet.context_interpreter.data_types.construct.impl;

import static org.junit.Assert.assertFalse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.context.IPosetOfCategories;
import com.tregouet.context_interpreter.context.impl.PosetOfCategories;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.inputs.impl.GenericFileReader;

public class AbstractConstructTest {

	private static Path shapes3 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes3.txt");
	List<IContextObject> shapes3Obj;
	IPosetOfCategories catRel3;
	
	@Before
	public void setUp() throws Exception {
		shapes3Obj = GenericFileReader.getContextObjects(shapes3);
		catRel3 = new PosetOfCategories(shapes3Obj);
	}

	@Test
	public void whenConstructsOfCategoriesOfTheSameContextComparedThenNeverSameHashCode() {
		boolean equals = false;
		Set<Integer> hashCodes = new HashSet<Integer>();
		Set<ICategory> cats = new HashSet<ICategory>(catRel3.getCategories());
		cats.remove(catRel3.getCatLatticeMin());
		for (ICategory cat : cats) {
			for (IConstruct construct : cat.getIntent()) {
				if (!hashCodes.add(construct.hashCode()))
					equals = true;
			}
		}
		assertFalse(equals);
	}
	
	@Test
	public void whenConstructsOfNonMinumumCategoriesOfTheSameContextComparedThenNeverEquals() {
		boolean equals = false;
		Set<IConstruct> constructs = new HashSet<IConstruct>();
		Set<ICategory> cats = new HashSet<ICategory>(catRel3.getCategories());
		cats.remove(catRel3.getCatLatticeMin());
		for (ICategory cat : cats) {
			for (IConstruct construct : cat.getIntent()) {
				if (!constructs.add(construct)) {
					equals = true;
				}
			}
		}
		assertFalse(equals);
	}

}
