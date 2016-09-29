package compiler.analysis;

import compiler.core.*;
import compiler.exceptions.*;
import compiler.generator.CodeGenerator;

import java.awt.datatransfer.StringSelection;
import java.util.*;

import javax.lang.model.type.DeclaredType;

public class SemanticImpl{
	
	public enum Operation {
		PLUS, MINUS, MULT, DIV, PERC, LE_OP, GE_OP, LESS_THAN, MORE_THAN, EQ_OP, NE_OP, AND_OP, OR_OP, NOT_OP;
	}

	private HashMap<String,Variable> variables = new HashMap<String,Variable>();
	private List<Type> secondaryTypes = new ArrayList<Type>();
	private ArrayList<Function> functions = new ArrayList<Function>();
	private List<Variable> tempVariables = new ArrayList<Variable>();
	private Stack<ScopedEntity> scopeStack = new Stack<ScopedEntity>();
	Program jProgram = new Program();
	private static Map<String, List<String>> tiposCompativeis = new HashMap<String, List<String>>();
	private static List<String> testingOperators = new ArrayList<String>();
	private static List<Type> BASIC_TYPES;
	
	private static SemanticImpl singleton;
	private Program javaProgram;
	static CodeGenerator codeGenerator;
	
	public static SemanticImpl getInstance(){
		if(singleton ==  null){
			singleton = new SemanticImpl();
			codeGenerator = new CodeGenerator();
			initCollections();
		}
		return singleton;
	}
	
	private static void initCollections() {
		initBasicTypes();		
		initTypeCompatibility();
		iniTestingOperators();
	}

	protected SemanticImpl(){
        javaProgram = new Program();
	}
	
	private static void initBasicTypes(){
		BASIC_TYPES = new ArrayList<Type>(){{
			add(new Type("int") );
			add(new Type("float")); 
			add(new Type("double")); 
			add(new Type("long"));
			add(new Type("char")); 
			add(new Type("void"));
			add(new Type("String"));
			add(new Type("boolean")); 
			add(new Type("Object"));
			add(new Type("Integer")); 
		}};
	}
	
	private static void initTypeCompatibility(){
		List<String> doubleCompTypes = new ArrayList<String>();
		doubleCompTypes.add("int");
		doubleCompTypes.add("float");
		doubleCompTypes.add("double");
		doubleCompTypes.add("long");
		
		List<String> floatCompTypes = new ArrayList<String>();
		floatCompTypes.add("int");
		floatCompTypes.add("float");
		floatCompTypes.add("long");
		
		List<String> longCompTypes = new ArrayList<String>();
		longCompTypes.add("long");
		longCompTypes.add("int");
		
		List<String> intCompTypes = new ArrayList<String>();
		intCompTypes.add("int");
		
		List<String> booleanCompTypes = new ArrayList<String>();
		booleanCompTypes.add("boolean");

		tiposCompativeis.put("double", doubleCompTypes);
		tiposCompativeis.put("float", floatCompTypes);
		tiposCompativeis.put("long", longCompTypes);
		tiposCompativeis.put("int", intCompTypes);
		tiposCompativeis.put("boolean", booleanCompTypes);
	}
	
	
	private static void iniTestingOperators(){
		testingOperators.add("<");
		testingOperators.add(">");
		testingOperators.add("<=");
		testingOperators.add(">=");
		testingOperators.add("==");
		testingOperators.add("!=");
	}


	private void createNewScope(ScopedEntity scope) {
		scopeStack.push(scope);
	}

	public void exitCurrentScope() throws InvalidFunctionException {
		ScopedEntity scoped = scopeStack.pop();
	}
	public void exitCurrentScope(Expression exp) throws InvalidFunctionException {
		ScopedEntity scoped = scopeStack.pop();
		if(scoped instanceof  Function){
			if(exp != null) {
				checkDeclaredAndReturnedType(scoped.getName(), ((Function) scoped).getDeclaredReturnType(), exp);
			}else{
				//System.out.println("O declared eh: "+((Function) scoped).getDeclaredReturnType());
				if(!((Function) scoped).getDeclaredReturnType().equals(new Type("void"))){
					throw new InvalidFunctionException("The function "+scoped.getName() +" is missing a return statement in the end of it");
				}
			}
		}
	}
	public ScopedEntity getCurrentScope() {
		return scopeStack.peek();
	}

	public void addFunctionAndNewScope(Function f) {
		functions.add(f);
		createNewScope(f);
	}

	public boolean checkVariableExistence(String variableName) {
		if(!scopeStack.isEmpty() && getCurrentScope().getVariable().get(variableName) != null){
			return true;
		}else{
			return variables.get(variableName) != null ? true : false;
		}
	}
	public boolean checkVariableExistenceLocal(String variableName) {
		if(!scopeStack.isEmpty() && getCurrentScope().getVariable().get(variableName) != null){
			return true;
		}else{
			return false;
		}
	}

	public boolean checkVariableExistenceGlobal(String variableName) {
		return variables.get(variableName) != null ? true : false;
	}
   public void checkFunctionExistence(Function temp) throws InvalidFunctionException {
		if(javaProgram.getFunctions().get(temp.getName()) != null){
			throw new InvalidFunctionException("ERROR: The function "+temp.getName()+" has already been declared!");
		}
	}

	public boolean checkValidExistingType(Type type) {
		return BASIC_TYPES.contains(type) || secondaryTypes.contains(type);
	}

	public boolean checkTypeCompatibility(Type leftType, Type rightType) {
		if (leftType.equals(rightType)){
			return true;
		} else {
			List<String> tipos = tiposCompativeis.get(leftType.getName());
			if(tipos == null) return false;
			return tipos.contains(rightType.getName());
		}
	}
	
	public void addType(Type type){
		if(!secondaryTypes.contains(type)){
			secondaryTypes.add(type);
			List<String> tipos = new ArrayList<String>();
			tipos.add(type.getName());
			tiposCompativeis.put(type.getName(), tipos);
		}
	}
	
	public boolean checkTypeOfAssignment(Variable variable, Expression exp) throws InvalidTypeAssignmentException{
		if (!variable.getType().equals(exp.getType())){
			throw new InvalidTypeAssignmentException("Alguma msg aqui");
		}
		return true;
	}
	
	public boolean isNumericExpression(Expression le, Expression re) throws InvalidOperationException{
		if(!le.isNumeric() || !re.isNumeric()){
			throw new InvalidOperationException("Not a numeric expression");
		}
		return true;
	}
	
	public boolean isNumericExpression(Expression le) throws InvalidOperationException{
        if(!le.isNumeric()){
            throw new InvalidOperationException("Not a numeric expression");
		}
		return true;
	}
	
	/**
	 * Valida uma variavel: 
	 * 	- se o tipo dela existe
	 *  - se o nome ja esta em uso
	 *  
	 * @param variable variable a ser validade 
	 * 
	 * @throws Exception
	 */
	private void validateVariable(Variable variable) throws Exception{
		if (checkVariableExistenceLocal(variable.getIdentifier())){
			throw new InvalidVariableException("Name already exists"); 
		}
		if (!checkValidExistingType(variable.getType())){
			throw new InvalidTypeException("Type non existing");
		}
	}
	private void validateVariableGlobal(Variable variable) throws Exception{
		if (checkVariableExistenceGlobal(variable.getIdentifier())){
			throw new InvalidVariableException("Name already exists");
		}
		if (!checkValidExistingType(variable.getType())){
			throw new InvalidTypeException("Type non existing");
		}
	}
	
	/**
	 * Valida uma variavel.
	 * Caso seja valida, adiciona a um mapa de variaveis sendo usadas.
	 *  
	 * @param variable variable a ser validade e posteriormente adicionada.
	 * 
	 * @throws Exception
	 */
	private void addVariable(Variable variable) throws Exception{
		for(String v : variables.keySet()){
			//System.out.println(v);
		}

		if(scopeStack.isEmpty()){
			validateVariableGlobal(variable);

			variables.put(variable.getIdentifier(),variable);
		}else{
			validateVariable(variable);
			getCurrentScope().addVariable(variable);
		}

		if (variable.getValue() != null){
			checkVariableAttribution(variable.getIdentifier(), variable.getValue());
		}
	}
	
	public void addVariablesFromTempList(Type type) throws Exception{
		for (Variable variable : tempVariables) {
			variable.setType(type);
			addVariable(variable);
		}
		
		tempVariables = new ArrayList<Variable>();
	}
	
	public void validateFunction(String functionName, ArrayList<Parameter> params, Type declaredType) throws InvalidFunctionException, InvalidParameterException{
		if(declaredType == null){
			throw new InvalidFunctionException("The function "+functionName +" is missing either a declared return type or a return statement in the end of it");
		}
		Function temp = new Function(functionName, params);
		if(params != null){
			for(Parameter p : params){
				variables.put(p.getIdentifier(), (Variable) p);
			}
			checkExistingParameter(params);
			checkFunctionExistence(temp);
		}
		temp.setDeclaredReturnedType(declaredType);
		addFunctionAndNewScope(temp);
	}

	private void hasReturn(Expression exp) throws InvalidFunctionException {
		if(!exp.getContext().equalsIgnoreCase("return")){
			throw new InvalidFunctionException("Missing a return statement");
		}
	}

	private void checkDeclaredAndReturnedType(String functionName,Type declaredType, Expression exp) throws InvalidFunctionException {
		if(!declaredType.equals(exp.getType())){
			throw new InvalidFunctionException("The function "+functionName+" didn't return the expected type: "+declaredType+". It returns "+exp.getType() + " instead");
		}
	}
	

	private void checkExistingParameter(ArrayList<Parameter> params) throws InvalidParameterException {
		for(int i=0; i<params.size();i++){
			for(int k=i+1;k<params.size();k++){
				if(params.get(i).getIdentifier().equals(params.get(k).getIdentifier())){
					throw new InvalidParameterException("The parameter: "+params.get(k).getIdentifier()+ " has been previously defined.");
				}
			}
		}
	}
	
	//FIXME: INCOMPLETE
	public Expression getExpression(Expression le, String md, Expression re) throws InvalidTypeException{
		//System.out.println("No getexpression " + "tipo 1: "+ le.getType().getName() + "   " + "tipo 2: "+ re.getType().getName());
		if (checkTypeCompatibility(le.getType(), re.getType()) || checkTypeCompatibility(re.getType(), le.getType())){
			Type newType =  getMajorType(le.getType(), re.getType());
			return new Expression(newType);
		}
		throw new InvalidTypeException("Not allowed!"); 
	}
	
	private Type getMajorType(Type type1, Type type2) {
		return tiposCompativeis.get(type1.getName()).contains(type2.getName()) ? type1: type2;
	}

	public void checkVariableAttribution(String id, Expression expression) throws InvalidVariableException, InvalidTypeException, InvalidFunctionException{
		if (!checkVariableExistence(id)){
			throw new InvalidVariableException("Variable doesn't exist"); 
		}
		//System.out.println(expression.getType());
		if (!expression.getType().equals(new Type("null")) && !checkValidExistingType(expression.getType())){
			throw new InvalidTypeException("Type non existing");
		}
		//System.out.println("AD");
		Type identifierType = findVariableByIdentifier(id).getType();
		//System.out.println(identifierType.getName());
		if(expression.getType().equals(new Type("null"))){return;}
		if (!checkTypeCompatibility(identifierType, expression.getType())){
			String exceptionMessage = String.format("Incompatible types! %s doesn't match %s", identifierType, expression.getType());
			throw new InvalidFunctionException(exceptionMessage);
		}
	}
	
	public Variable findVariableByIdentifier(String variableName){
		if(!scopeStack.isEmpty() && getCurrentScope().getVariable().get(variableName) != null){
			return getCurrentScope().getVariable().get(variableName);
		}else{
			return variables.get(variableName);
		}

	}
	
	public void validateVariableName(String variableName) throws InvalidVariableException{
		if (!checkVariableExistence(variableName)){
			throw new InvalidVariableException("Variable doesn't exist"); 
		}
	}
	
	public void addSupertype(String className, String superClassName) throws InvalidTypeException{
		if (superClassName != null) {
			if (tiposCompativeis.containsKey(superClassName)){
				tiposCompativeis.get(superClassName).add(className);
				return;
			}
			
			throw new InvalidTypeException("Superclass doesn't exist");
		}
	}
	
	private void checkTestingExpression(Expression le, String operator,  Expression re) throws InvalidOperatorException, InvalidOperationException{
		// operador eh valido
		if (!testingOperators.contains(operator)){
			String message = String.format("This operator: %s is not allowed for testing operations.", operator); 
			throw new InvalidOperatorException(message);
		}
		// exp sao de tipos equivalentes
		if (!checkTypeCompatibility(le.getType(), re.getType())
				&& !checkTypeCompatibility(re.getType(), le.getType())){
			String message = String.format("This testing operation ( %s ) is not avaiable for the types %s and %s", operator, le.getType(), re.getType()); 
			throw new InvalidOperationException(message);
		}
	}
	
	public void validateIfElseStatement(Expression exp) throws InvalidIfStatementException{
		if(exp == null || !exp.getType().equals(new Type("boolean"))){
			throw new InvalidIfStatementException("The given expression's type: "+exp.getType() +" isn't valid for a IF/ELSE statement.A boolean is required");
		}
	}
	
	public Expression getTestingExpression(Expression le, String operator,  Expression re) throws InvalidOperationException, InvalidOperatorException{
		checkTestingExpression(le, operator, re);
		return new Expression(new Type("boolean"));
		
	}
	/* Auxiliary functions*/
	
	public void addVariableToTempList(Variable var){
		tempVariables.add(var);
	}
	
	public CodeGenerator getCodeGenerator(){
		return codeGenerator;
	}
	
	public void getExpressionForOperation(Operation op, Expression e1, Expression e2) {
		System.out.println("CHAAAAMMMOOOU");
		switch (op) {
		case AND_OP:
			codeGenerator.generateLDCode(e1);
			codeGenerator.generateLDCode(e2);
			codeGenerator.generateMULCode();
		case OR_OP:
			codeGenerator.generateLDCode(e1);
			codeGenerator.generateLDCode(e2);
			codeGenerator.generateADDCode();
		case NOT_OP:
			codeGenerator.generateLDCode(e1);
			codeGenerator.generateNOTCode();
		case MINUS:
			codeGenerator.generateLDCode(e1);
			codeGenerator.generateLDCode(e2);
			codeGenerator.generateSUBCode();
		case MULT:
			codeGenerator.generateLDCode(e1);
			codeGenerator.generateLDCode(e2);
			codeGenerator.generateMULCode();
		case PLUS:
			codeGenerator.generateLDCode(e1);
			codeGenerator.generateLDCode(e2);
			codeGenerator.generateADDCode();
		case DIV:
			codeGenerator.generateLDCode(e1);
			codeGenerator.generateLDCode(e2);
			codeGenerator.generateDIVCode();
		default:
			break;
		}
		
	}

	
}
