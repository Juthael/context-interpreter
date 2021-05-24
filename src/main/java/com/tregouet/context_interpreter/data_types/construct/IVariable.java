package com.tregouet.context_interpreter.data_types.construct;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.data_types.representation.op.IDSOperator;

/**
 * <p>
 * A formal variable, used as a placeholder for some unspecified part of an abstract construct ({@link IConstruct}). 
 * Variables can be assigned a value by domain-specific operators ({@link IDSOperator}), which leads to the generation of 
 * a new (and less abstract) construct. 
 * </p> 
 * 
 * <p>
 * A given variable should never be found in distinct constructs, nor in similar constructs belonging to the intent of 
 * distinct categories ({@link ICategory}). This way, a variable can be referred to as a <i>dimension</i> :  
 * its biding by a domain-specific operator yields a function that expresses what is common to all constructs obtained by 
 * this variable's assignment of a <i>value</i>, and the set of accepted values are the attributes accessible in this dimension.
 * </p>
 * 
 * @author Gael Tregouet
 *
 */
public interface IVariable extends ISymbol {

	/**
	 * 
	 * @return the number of times this variable has been assigned a value
	 */
	int getAssignmentCount();
	
	/**
	 * 
	 * @return this variable's name
	 */
	char getVarChar();
	
	/**
	 * Must be called every time this variable is assigned a value by an operator.
	 * @see IDSOperator
	 */
	void incrementAssignmentCount();
	
}
