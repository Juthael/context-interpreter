package com.tregouet.context_interpreter.context.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.context_interpreter.context.ITree;
import com.tregouet.context_interpreter.context.IUpperSemiLattice;
import com.tregouet.context_interpreter.context.impl.Tree;
import com.tregouet.context_interpreter.context.impl.UpperSemiLattice;

public class TreeFinderTest {

	String max = "MAX";
	String a = "A";
	String b = "B";
	String c = "C";
	String ab = "AB";
	String ac = "AC";
	String bc = "BC";
	Map<String, Set<String>> abc;
	Map<String, Set<String>> acb;
	Map<String, Set<String>> bac;
	Map<String, Set<String>> bca;
	Map<String, Set<String>> cab;
	Map<String, Set<String>> cba;
	Map<String, Set<String>> relation = new HashMap<String, Set<String>>();
	IUpperSemiLattice<String> uSL;
	Set<ITree<String>> expectedTrees = new HashSet<ITree<String>>();
	
	@Before
	public void setUp() throws Exception {
		relation.put(max, new HashSet<String>());
		relation.put(a, new HashSet<String>());
		relation.put(b, new HashSet<String>());
		relation.put(c, new HashSet<String>());
		relation.put(ab, new HashSet<String>());
		relation.put(ac, new HashSet<String>());
		relation.put(bc, new HashSet<String>());
		setRelations();
		uSL = new UpperSemiLattice<String>(relation);
		expectedTrees.add(new Tree<String>(abc));
		expectedTrees.add(new Tree<String>(acb));
		expectedTrees.add(new Tree<String>(bac));
		expectedTrees.add(new Tree<String>(bca));
		expectedTrees.add(new Tree<String>(cab));
		expectedTrees.add(new Tree<String>(cba));
	}

	@Test
	public void whenTreesRequestedThenExpectedReturned() {
		Set<ITree<String>> returnedTrees;
		TreeFinder<String> tF = new TreeFinder<String>(uSL);
		returnedTrees = tF.getTrees();
		assertTrue(returnedTrees.equals(expectedTrees));
	}
	
	private void setRelations() {
		setABC();
		setACB();
		setBAC();
		setBCA();
		setCAB();
		setCBA();
		setRelation();
	}
	
	private void setABC() {
		abc = new HashMap<String, Set<String>>();
		for (String key : relation.keySet()) {
			abc.put(key, new HashSet<String>());
		}
		abc.remove(c);
		abc.get(max).add(a);
		abc.get(max).add(b);
		abc.get(max).add(ab);
		abc.get(max).add(ac);
		abc.get(max).add(bc);
		abc.get(a).add(ab);
		abc.get(a).add(ac);
		abc.get(b).add(bc);	
	}
	
	private void setACB() {
		acb = new HashMap<String, Set<String>>();
		for (String key : relation.keySet()) {
			acb.put(key, new HashSet<String>());
		}	
		acb.remove(b);
		acb.get(max).add(a);
		acb.get(max).add(c);
		acb.get(max).add(ab);
		acb.get(max).add(ac);
		acb.get(max).add(bc);
		acb.get(a).add(ab);
		acb.get(a).add(ac);
		acb.get(c).add(bc);
	}
	
	private void setBAC() {
		bac = new HashMap<String, Set<String>>();
		for (String key : relation.keySet()) {
			bac.put(key, new HashSet<String>());
		}
		bac.remove(c);
		bac.get(max).add(a);
		bac.get(max).add(b);
		bac.get(max).add(ab);
		bac.get(max).add(ac);
		bac.get(max).add(bc);
		bac.get(b).add(ab);
		bac.get(b).add(bc);
		bac.get(a).add(ac);	
	}
	
	private void setBCA() {
		bca = new HashMap<String, Set<String>>();
		for (String key : relation.keySet()) {
			bca.put(key, new HashSet<String>());
		}	
		bca.remove(a);
		bca.get(max).add(b);
		bca.get(max).add(c);
		bca.get(max).add(ab);
		bca.get(max).add(ac);
		bca.get(max).add(bc);
		bca.get(b).add(ab);
		bca.get(b).add(bc);
		bca.get(c).add(ac);	
	}
	
	private void setCAB() {
		cab = new HashMap<String, Set<String>>();
		for (String key : relation.keySet()) {
			cab.put(key, new HashSet<String>());
		}
		cab.remove(b);
		cab.get(max).add(a);
		cab.get(max).add(c);
		cab.get(max).add(ab);
		cab.get(max).add(ac);
		cab.get(max).add(bc);
		cab.get(c).add(ac);
		cab.get(c).add(bc);
		cab.get(a).add(ab);	
	}
	
	private void setCBA() {
		cba = new HashMap<String, Set<String>>();
		for (String key : relation.keySet()) {
			cba.put(key, new HashSet<String>());
		}	
		cba.remove(a);
		cba.get(max).add(b);
		cba.get(max).add(c);
		cba.get(max).add(ab);
		cba.get(max).add(ac);
		cba.get(max).add(bc);
		cba.get(c).add(ac);
		cba.get(c).add(bc);
		cba.get(b).add(ab);
	}
	
	private void setRelation() {
		relation.get(max).add(a);
		relation.get(max).add(b);
		relation.get(max).add(c);
		relation.get(max).add(ab);
		relation.get(max).add(ac);
		relation.get(max).add(bc);
		relation.get(a).add(ab);
		relation.get(a).add(ac);
		relation.get(b).add(ab);
		relation.get(b).add(bc);
		relation.get(c).add(ac);
		relation.get(c).add(bc);
	}

}
