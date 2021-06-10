package com.tregouet.context_interpreter;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		int[] maxVal = new int[4];
		maxVal[0] = 2;
		maxVal[1] = 3;
		maxVal[2] = 4;
		maxVal[3] = 5;
		int[] coords = new int[4];
		coords[0] = -1;
		while (nextCoord(coords, maxVal))
			System.out.println(Arrays.toString(coords));
		
	}
	
	private static boolean nextCoord(int[] coords, int[] dimensions){
		for(int i = 0 ; i < coords.length ; ++i) {
			if (++coords[i] < dimensions[i])
				return true;
			else coords[i] = 0;
	    }
	    return false;
    }	

}
