package com.tregouet.context_interpreter.data_types.representation.op.impl;

import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

public abstract class DSOperator implements IDSOperator {

	private static int lastID = 0;
	
	private static double customLog(double base, double logNumber) {
	    return Math.log(logNumber) / Math.log(base);
	}
	protected static double calculateCost(int nbOfApplications, int nbOfAssignments) {
		double probOfApplication = nbOfApplications / nbOfAssignments;
		return -customLog(2, probOfApplication);
	}
	private final int ctxtStateID;
	
	private final int iD = lastID++;
	
	protected int applicationsCount = 0;

	public DSOperator(int ctxtStateID) {
		this.ctxtStateID = ctxtStateID;
	}

	@Override
	public int getContextStateID() {
		return ctxtStateID;
	}

	@Override
	public abstract double getCost();

	@Override
	public int getID() {
		return iD;
	}

	@Override
	public int getNbOfApplications() {
		return applicationsCount;
	}
	
	@Override
	public abstract int getNbOfBoundVar();
	
	@Override
	public abstract void incCounters();

}
