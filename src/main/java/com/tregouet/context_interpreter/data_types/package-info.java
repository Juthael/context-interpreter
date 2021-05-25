/**
 * <p>
 * This package contains the input and output data types of the context interpreter. 
 * </p>
 * 
 * <p>
 * A context interpreter accepts <b>contexts</b> (({@link com.tregouet.context_interpreter.context.IContext}) as inputs, 
 * i.e. sets of objects. Each object 
 * ({@link com.tregouet.context_interpreter.data_types.construct.IContextObject}) is "dumbly" described in a 
 * context-blind fashion, by a set of constructs ({@link com.tregouet.context_interpreter.data_types.construct.IConstruct}) 
 * such as the "words" that a context-free grammar can generate. This raw description takes no account of the context that 
 * objects are part of  : the similarities that can exist between objects do not influence the way they are characterized.
 * Actually, these similarities cannot even be described or perceived at this stage.    
 * </p>
 * 
 * <p>
 * A context interpreter outputs <b>representations</b> 
 * ({@link com.tregouet.context_interpreter.data_types.representation.IRepresentation}), based 
 * on <i>ad hoc</i> categories extracted from the context. Representations allow the description of objects in a 
 * context-sensitive manner, as well as measures of similarity between objects and production of inferences. Representations are 
 * also associated with a cost ; thus, not only can a context interpreter generate alternative representations of a given 
 * context, but it can also make predictions about which of theses acceptable representations will be considered as optimal.   
 * </p>
 * 
 * @author Gael Tregouet
 *
 */
package com.tregouet.context_interpreter.data_types;