package com.tregouet.context_interpreter.data_types.representation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.context_interpreter.data_types.construct.AVariable;
import com.tregouet.context_interpreter.data_types.construct.IConstruct;
import com.tregouet.context_interpreter.data_types.representation.IRepresentation;
import com.tregouet.context_interpreter.data_types.representation.ITree;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;
import com.tregouet.context_interpreter.data_types.representation.op.IMonoBinderOp;

public class TreeRepresentation implements ITree, IRepresentation {
	
	private IDSOperator token;
	private final List<ITree> children;
	
	public TreeRepresentation(IDSOperator token) {
		this.token = token;
		children = new ArrayList<ITree>();
	}
	
	public TreeRepresentation(IDSOperator token, List<ITree> children) {
		this.token = token;
		this.children = children;
	}	

	@Override
	public Map<AVariable, Set<IConstruct>> asDomainSpecificGrammar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<IConstruct, IDSOperator> asGenusDifferentiaDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String asLambdaExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String asStringOfIDs() {
		StringBuilder sB = new StringBuilder();
		sB.append(token.getID());
		if (!getChildren().isEmpty()) {
			sB.append("(");
			int childIdx = 0;
			for (ITree tree : getChildren()) {
				sB.append(getChildren().size() > 1 ? "(" : "");
				sB.append(tree.asStringOfIDs());
				sB.append(getChildren().size() > 1 ? ")" : "");
				sB.append(childIdx < getChildren().size() - 1 ? " ^ " : "");
				childIdx ++;
			}
			sB.append(")");
		}
		return sB.toString();
	}

	@Override
	public int compareTo(IRepresentation other) {
		return Double.compare(getCost(), other.getCost());
	}

	@Override
	public List<ITree> getChildren() {
		return children;
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<IMonoBinderOp> getMonoBinderOps() {
		Set<IMonoBinderOp> monobinders = new HashSet<IMonoBinderOp>();
		if (token instanceof IMonoBinderOp)
			monobinders.add((IMonoBinderOp) token);
		for (ITree tree : getChildren())
			monobinders.addAll(tree.getMonoBinderOps());
		return monobinders;
	}

	@Override
	public Set<IDSOperator> getOperators() {
		Set<IDSOperator> operators = new HashSet<IDSOperator>();
		operators.add(token);
		for (ITree tree : getChildren())
			operators.addAll(tree.getOperators());
		return operators;
	}

	@Override
	public IDSOperator getToken() {
		return token;
	}

	@Override
	public void incCounters() {
		token.incCounters();
		for (ITree tree : getChildren())
			tree.incCounters();
	}

	@Override
	public boolean isLeaf() {
		return (children.isEmpty());
	}

	@Override
	public void replaceMonoBinderOp(Map<IMonoBinderOp, IDSOperator> replacedToSubstitute) {
		if (replacedToSubstitute.containsKey(token))
			token = replacedToSubstitute.get(token);
		for (ITree tree : getChildren())
			tree.replaceMonoBinderOp(replacedToSubstitute);
	}
	
}
