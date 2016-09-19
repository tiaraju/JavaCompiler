package compiler.core;


public class Type{

	private String typeName;

	public Type(String typeName) {
		this.typeName = typeName;
	}

	public String getName() {
		return this.typeName;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Type))
			return false;
		return this.getName().equals(((Type) obj).getName());
	}

}