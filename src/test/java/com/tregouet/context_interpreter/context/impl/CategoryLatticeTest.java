package com.tregouet.context_interpreter.context.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.context_interpreter.context.ICategoryLattice;
import com.tregouet.context_interpreter.context.ICategoryTree;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.io.inputs.impl.GenericFileReader;
import com.tregouet.context_interpreter.io.outputs.exceptions.VisualizationException;

public class CategoryLatticeTest {

	private static Path shapes3 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes3.txt");
	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes2.txt");
	private static Path shapes4 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes4.txt");
	List<IContextObject> shapes3Obj;
	ICategoryLattice catRel3;
	List<IContextObject> shapes2Obj;
	ICategoryLattice catRel2;
	List<IContextObject> shapes4Obj;
	ICategoryLattice catRel4;
	
	@SuppressWarnings("unused")
	@Before
	public void setUp() throws Exception {
		shapes3Obj = GenericFileReader.getContextObjects(shapes3);
		catRel3 = new CategoryLattice(shapes3Obj);
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		catRel2 = new CategoryLattice(shapes2Obj);
		shapes4Obj = GenericFileReader.getContextObjects(shapes4);
		catRel4 = new CategoryLattice(shapes4Obj);
	}

	@Test
	public void whenTreesRequestedThenReturned() throws VisualizationException {
		catRel2.buildSuccessorRelationGraph("catRel2");
		Set<ICategoryTree> trees = catRel2.getCategoryTrees();
		/*
		int idx = 0;
		for (ICategoryTree tree : trees)
			tree.buildSuccessorRelationGraph(Integer.toString(idx++));
		*/
		System.out.println(trees.size());
		assertTrue(!trees.isEmpty());
	}

}
