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

import com.tregouet.context_interpreter.context.utils.IntentBldr;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.construct.IContextObject;
import com.tregouet.context_interpreter.data_types.construct.impl.AbstractConstruct;
import com.tregouet.context_interpreter.data_types.construct.impl.Construct;
import com.tregouet.context_interpreter.inputs.impl.GenericFileReader;
import com.tregouet.subseq_finder.ISymbolSeq;

public class IntentBldrTest {

	private static Path shapes1 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes1.txt");
	@SuppressWarnings("unused")
	private IContextObject alpha;
	@SuppressWarnings("unused")
	private IContextObject beta;
	@SuppressWarnings("unused")
	private IContextObject gamma;
	private List<IContextObject> alphaBetaGamma;
	private List<IContextObject> alphaBeta = new ArrayList<IContextObject>();
	private List<IContextObject> alphaGamma = new ArrayList<IContextObject>();
	private List<IContextObject> betaGamma = new ArrayList<IContextObject>();
	
	@Before
	public void setUp() throws Exception {
		alphaBetaGamma = GenericFileReader.getContextObjects(shapes1);
		alphaBeta.add(alphaBetaGamma.get(0));
		alphaBeta.add(alphaBetaGamma.get(1));
		alphaGamma.add(alphaBetaGamma.get(0));
		alphaGamma.add(alphaBetaGamma.get(2));
		betaGamma.add(alphaBetaGamma.get(1));
		betaGamma.add(alphaBetaGamma.get(2));		
		/*
		alpha = alphaBetaGamma.get(0);
		System.out.println(alpha.toString());		
		beta = alphaBetaGamma.get(1);
		System.out.println(beta.toString());
		gamma = alphaBetaGamma.get(2);
		System.out.println(gamma.toString());
		*/
	}

	@Test
	public void whenIntentRequestedThenExpectedReturned() {
		Set<IConstruct> alphaBetaGammaIntent = IntentBldr.getIntent(alphaBetaGamma);
		Set<IConstruct> alphaBetaGammaExpected = setAlphaBetaGammaExpected();
		boolean alphaBetaGammaIntentAsExpected = alphaBetaGammaIntent.equals(alphaBetaGammaExpected);
		Set<IConstruct> alphaBetaIntent = IntentBldr.getIntent(alphaBeta);
		Set<IConstruct> alphaBetaExpected = setAlphaBetaExpected();
		boolean alphaBetaIntentAsExpected = alphaBetaIntent.equals(alphaBetaExpected);
		Set<IConstruct> alphaGammaIntent = IntentBldr.getIntent(alphaGamma);
		Set<IConstruct> alphaGammaExpected = setAlphaGammaExpected();
		boolean alphaGammaIntentAsExpected = alphaGammaIntent.equals(alphaGammaExpected);
		Set<IConstruct> betaGammaIntent = IntentBldr.getIntent(betaGamma);
		Set<IConstruct> betaGammaExpected = setBetaGammaExpected();
		boolean betaGammaIntentAsExpected = betaGammaIntent.equals(betaGammaExpected);
		
		for (IConstruct construct : alphaBetaGammaIntent)
			System.out.println(construct.toString());
		
		assertTrue(alphaBetaGammaIntentAsExpected 
				&& alphaBetaIntentAsExpected
				&& alphaGammaIntentAsExpected
				&& betaGammaIntentAsExpected);
	}
	
	@Test
	public void whenSqToMaxSubsqRequestedThenExpectedReturned() {
		Map<ISymbolSeq, Set<ISymbolSeq>> sqToMaxSubsq = IntentBldr.getSubsqToMaxSubsq(alphaBetaGamma);
		/*
		for (ISymbolSeq symbolSeq : sqToMaxSubsq.keySet()) {
			System.out.println("SYMBOL_SEQ : " + symbolSeq.toString());
			System.out.println("MAX_COMMON_SUBSEQ : ");
			List<ISymbolSeq> maxCommonSubsq = new ArrayList<ISymbolSeq>(sqToMaxSubsq.get(symbolSeq));
			for (int i = 0 ; i < maxCommonSubsq.size() ; i++) {
				System.out.print(maxCommonSubsq.get(i).toString());
				if (i < maxCommonSubsq.size() - 1)
					System.out.println(" ; ");
			}
			System.out.println(System.lineSeparator() + System.lineSeparator());
		}
		*/
		Map<String, Set<String>> returnedMap = setStringMap(sqToMaxSubsq);
		Map<String, Set<String>> expectedMap = setExpectedStringMap();
		boolean asExpected = returnedMap.equals(expectedMap);
		assertTrue(asExpected);
	}
	
	private Map<String, Set<String>> setStringMap(Map<ISymbolSeq, Set<ISymbolSeq>> sqToMaxSubsq){
		Map<String, Set<String>> stringMap = new HashMap<String, Set<String>>();
		for (ISymbolSeq seq : sqToMaxSubsq.keySet()) {
			Set<String> maxSubsqString = new HashSet<String>();
			for (ISymbolSeq maxSubsq : sqToMaxSubsq.get(seq))
				maxSubsqString.add(maxSubsq.toString());
			stringMap.put(seq.toString(), maxSubsqString);
		}
		return stringMap;
	}
	
	private Map<String, Set<String>> setExpectedStringMap(){
		Map<String, Set<String>> expected = new HashMap<String, Set<String>>();
		
		String alpha1 = "figure forme carré";
		String alpha1SubsqMax1 = "figure forme _";
		Set<String> alpha1Subsqs = new HashSet<String>();
		alpha1Subsqs.add(alpha1SubsqMax1);
		expected.put(alpha1, alpha1Subsqs);
		
		String alpha2 = "figure trait épaisseur épais";
		String alpha2SubsqMax1 = "figure trait épaisseur _";
		Set<String> alpha2Subsqs = new HashSet<String>();
		alpha2Subsqs.add(alpha2SubsqMax1);
		expected.put(alpha2, alpha2Subsqs);
		
		String alpha3 = "figure trait couleur rouge";
		String alpha3SubsqMax1 = "figure trait couleur _";
		Set<String> alpha3Subsqs = new HashSet<String>();
		alpha3Subsqs.add(alpha3SubsqMax1);
		expected.put(alpha3, alpha3Subsqs);
		
		String alpha4 = "figure fond couleur bleu";
		String alpha4SubsqMax1 = "figure fond _ couleur _";
		String alpha4SubsqMax2 = "figure _ couleur bleu";
		Set<String> alpha4Subsqs = new HashSet<String>();
		alpha4Subsqs.add(alpha4SubsqMax1);
		alpha4Subsqs.add(alpha4SubsqMax2);
		expected.put(alpha4, alpha4Subsqs);
		
		String beta1 = "figure forme triangle";
		String beta1SubsqMax1 = "figure forme _"; 
		Set<String> beta1Subsqs = new HashSet<String>();
		beta1Subsqs.add(beta1SubsqMax1);
		expected.put(beta1, beta1Subsqs);
		
		String beta2 = "figure trait épaisseur fin";
		String beta2SubsqMax1 = "figure trait épaisseur _"; 
		Set<String> beta2Subsqs = new HashSet<String>();
		beta2Subsqs.add(beta2SubsqMax1);
		expected.put(beta2, beta2Subsqs);
		
		String beta3 = "figure trait couleur bleu";
		String beta3SubsqMax1 = "figure trait couleur _";
		String beta3SubsqMax2 = "figure _ couleur bleu";
		Set<String> beta3Subsqs = new HashSet<String>();
		beta3Subsqs.add(beta3SubsqMax1);
		beta3Subsqs.add(beta3SubsqMax2);
		expected.put(beta3, beta3Subsqs);
		
		String beta4 = "figure fond rayures orientation diagonale";
		String beta4SubsqMax1 = "figure fond _"; 
		Set<String> beta4Subsqs = new HashSet<String>();
		beta4Subsqs.add(beta4SubsqMax1);
		expected.put(beta4, beta4Subsqs);
		
		String beta5 = "figure fond rayures couleur rouge";
		String beta5SubsqMax1 = "figure fond _ couleur _"; 
		Set<String> beta5Subsqs = new HashSet<String>();
		beta5Subsqs.add(beta5SubsqMax1);
		expected.put(beta5, beta5Subsqs);
		
		String gamma1 = "figure forme triangle";
		String gamma1SubsqMax1 = "figure forme _"; 
		Set<String> gamma1Subsqs = new HashSet<String>();
		gamma1Subsqs.add(gamma1SubsqMax1);
		expected.put(gamma1, gamma1Subsqs);
		
		String gamma2 = "figure trait épaisseur épais";
		String gamma2SubsqMax1 = "figure trait épaisseur _"; 
		Set<String> gamma2Subsqs = new HashSet<String>();
		gamma2Subsqs.add(gamma2SubsqMax1);
		expected.put(gamma2, gamma2Subsqs);
		
		String gamma3 = "figure trait couleur bleu";
		String gamma3SubsqMax1 = "figure trait couleur _";
		String gamma3SubsqMax2 = "figure _ couleur bleu";
		Set<String> gamma3Subsqs = new HashSet<String>();
		gamma3Subsqs.add(gamma3SubsqMax1);
		gamma3Subsqs.add(gamma3SubsqMax2);
		expected.put(gamma3, gamma3Subsqs);
		
		String gamma4 = "figure fond rayures orientation horizontale";
		String gamma4SubsqMax1 = "figure fond _"; 
		Set<String> gamma4Subsqs = new HashSet<String>();
		gamma4Subsqs.add(gamma4SubsqMax1);
		expected.put(gamma4, gamma4Subsqs);
		
		String gamma5 = "figure fond rayures couleur vert";
		String gamma5SubsqMax = "figure fond _ couleur _"; 
		Set<String> gamma5Subsqs = new HashSet<String>();
		gamma5Subsqs.add(gamma5SubsqMax);
		expected.put(gamma5, gamma5Subsqs);
		
		return expected;
	}
	
	private Set<IConstruct> setAlphaBetaGammaExpected(){
		Set<IConstruct> expected = new HashSet<IConstruct>();
		Set<String> maxSubsqsString = new HashSet<String>();
		maxSubsqsString.add("figure forme _");
		maxSubsqsString.add("figure trait épaisseur _");
		maxSubsqsString.add("figure trait couleur _");
		maxSubsqsString.add("figure _ couleur bleu");
		maxSubsqsString.add("figure fond _");
		maxSubsqsString.add("figure fond _ couleur _");
		for (String maxSubsq : maxSubsqsString)
			if (maxSubsq.contains("_"))
				expected.add(new AbstractConstruct(maxSubsq.split(" ")));
			else expected.add(new Construct(maxSubsq.split(" ")));
		return expected;
	}
	
	private Set<IConstruct> setAlphaBetaExpected(){
		Set<IConstruct> expected = new HashSet<IConstruct>();
		Set<String> maxSubsqsString = new HashSet<String>();
		maxSubsqsString.add("figure forme _");
		maxSubsqsString.add("figure trait épaisseur _");
		maxSubsqsString.add("figure trait couleur _");
		maxSubsqsString.add("figure _ couleur rouge");
		maxSubsqsString.add("figure _ couleur bleu");
		maxSubsqsString.add("figure fond _ couleur _");
		maxSubsqsString.add("figure fond _");
		for (String maxSubsq : maxSubsqsString)
			if (maxSubsq.contains("_"))
				expected.add(new AbstractConstruct(maxSubsq.split(" ")));
			else expected.add(new Construct(maxSubsq.split(" ")));
		return expected;
	}
	
	private Set<IConstruct> setAlphaGammaExpected(){
		Set<IConstruct> expected = new HashSet<IConstruct>();
		Set<String> maxSubsqsString = new HashSet<String>();
		maxSubsqsString.add("figure forme _");
		maxSubsqsString.add("figure trait épaisseur épais");
		maxSubsqsString.add("figure trait couleur _");
		maxSubsqsString.add("figure fond _");
		maxSubsqsString.add("figure _ couleur bleu");
		maxSubsqsString.add("figure fond _ couleur _");
		for (String maxSubsq : maxSubsqsString)
			if (maxSubsq.contains("_"))
				expected.add(new AbstractConstruct(maxSubsq.split(" ")));
			else expected.add(new Construct(maxSubsq.split(" ")));
		return expected;
	}
	
	private Set<IConstruct> setBetaGammaExpected(){
		Set<IConstruct> expected = new HashSet<IConstruct>();
		Set<String> maxSubsqsString = new HashSet<String>();
		maxSubsqsString.add("figure forme triangle");
		maxSubsqsString.add("figure trait épaisseur _");
		maxSubsqsString.add("figure trait couleur bleu");
		maxSubsqsString.add("figure fond rayures orientation _");
		maxSubsqsString.add("figure fond rayures couleur _");
		for (String maxSubsq : maxSubsqsString)
			if (maxSubsq.contains("_"))
				expected.add(new AbstractConstruct(maxSubsq.split(" ")));
			else expected.add(new Construct(maxSubsq.split(" ")));
		return expected;
	}
	
	

}
