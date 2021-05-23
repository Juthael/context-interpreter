package com.tregouet.context_interpreter.context.impl;

import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.context_interpreter.context.utils.IntentBldr;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.inputs.impl.GenericFileReader;

public class IntentBldrTest {

	private static Path shapes1 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes1.txt");
	@SuppressWarnings("unused")
	private IContextObject alpha;
	@SuppressWarnings("unused")
	private IContextObject beta;
	@SuppressWarnings("unused")
	private IContextObject gamma;
	private List<IContextObject> shapes;
	
	@Before
	public void setUp() throws Exception {
		shapes = GenericFileReader.getContextObjects(shapes1);
		/*
		alpha = shapes.get(0);
		System.out.println(alpha.toString());		
		beta = shapes.get(1);
		System.out.println(beta.toString());
		gamma = shapes.get(2);
		System.out.println(gamma.toString());
		*/
	}

	@Test
	public void test() {
		Set<IConstruct> alphaBetaGammaIntent = IntentBldr.getIntent(shapes);
		for (IConstruct construct : alphaBetaGammaIntent)
			System.out.println(construct.toString());
		fail("Not yet implemented");
	}

}
