package compiler.analysis;

import java.util.ArrayList;
import java.util.List;

import compiler.core.Type;

public class SemanticImpl implements Semantic {

	public ArrayList<String> variableNames = new ArrayList<String>();
	public ArrayList<String> methodNames = new ArrayList<String>();

	private final List<Type> BASIC_TYPES = new ArrayList<Type>(){{
		add(new Type("int") );
		add(new Type("float")); 
		add(new Type("double")); 
		add(new Type("long"));
		add(new Type("char")); 
		add(new Type("void"));
		add(new Type("String"));
		add(new Type("boolean")); 
	}};

	@Override
	public boolean checkVariableExistence(String variableName) {
		return variableName.contains(variableName);
	
	}

	@Override
	public boolean checkMethodExistence(String methodName) {
		return methodName.contains(methodName);
	}

	public boolean checkValidExistingType(Type type) {
		return BASIC_TYPES.contains(type);
	}

	@Override
	public boolean checkTypeCompatibility(Type leftType, Type rightType) {
		return false;
	}
	
	public void addVariable(String variable){
		this.variableNames.add(variable);
	}


}
