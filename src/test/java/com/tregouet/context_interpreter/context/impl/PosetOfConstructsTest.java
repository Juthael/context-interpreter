package com.tregouet.context_interpreter.context.impl;

import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

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
		boolean generatesOther;
		boolean fromSuperCat;
		List<IConstruct> constructs = new ArrayList<IConstruct>(constRel3.getRelation().keySet());
		for () //HERE
		
	}
	
	@Test
	public void whenMaximumRequestedThen1VarConstructReturned() {
		
	}
	
	@Test
	public void whenMinimaRequestedThenUnionOfObjIntentsReturned() {
		
	}
	
	@Test
	public void whenSpanningChainsRequestedThenReallySpanning() {
		
	}
	
	@Test
	public void whenSpanningChainsrequestedThenOneOreMoreChainForEachLeaf() {
		
	}
	
	@Test
	public void whenSuccessorRequestedThenReturnedIsAnInstanceOfParam() {
		
	}
	
	@Test
	public void whenFilteredPosetRequestedThenReturned() {
		
	}
	
}
