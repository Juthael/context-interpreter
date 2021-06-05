package com.tregouet.context_interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		int[] oneToFour = new int[] {1, 2, 3, 4};
		int[] twoToFive = new int[] {2, 3, 4, 5};
		Set<Integer> oneToFourSet = new HashSet<Integer>();
		Set<Integer> twoToFiveSet = new HashSet<Integer>();
		for (int eger1 : oneToFour)
			oneToFourSet.add(eger1);
		for (int eger2 : twoToFive)
			twoToFiveSet.add(eger2);
		System.out.println("{1 2 3 4} before 1234.removeAll(2345) : ");
		System.out.println(oneToFourSet);
		System.out.println(System.lineSeparator() + "{2 3 4 5} before 1234.removeAll(2345) : ");
		System.out.println(twoToFiveSet);
		System.out.println("********************");
		
		oneToFourSet.removeAll(twoToFiveSet);
		System.out.println(System.lineSeparator() + "{1 2 3 4} after 1234.removeAll(2345) : ");
		System.out.println(oneToFourSet);
		System.out.println(System.lineSeparator() + "{2 3 4 5} after 1234.removeAll(2345) : ");
		System.out.println(twoToFiveSet);
	}

	public Test() {
		// TODO Auto-generated constructor stub
	}

}
