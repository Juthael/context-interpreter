package com.tregouet.context_interpreter;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int index = 0;
		for (char curr = 'a' ; curr <= 'z' ; curr++) {
			System.out.println(index + ": " + curr);
			index++;
		}
		for (char curr = 'α' ; curr <= 'ω' ; curr++) {
			System.out.println(index + ": " + curr);
			index++;
		}
	}

}
