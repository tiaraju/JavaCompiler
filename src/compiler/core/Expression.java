package compiler.core;


public class Expression {
	private Type type;
	private String value;
	
	public Expression(Type t, String value) {
		this.type = t;
		this.value = value;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
}
