package examples;

public class Test(){
	
	public String begginTest(){
		boolean a = true;
		return a;
	}
	
	public void logicalOP(){
		boolean t = true;
		boolean f = false;
		boolean a = t && f || t != true || t==f && !f;
		bollean b = t ^ f;
	}
	
	public void nullOp(){
		a = null;
	}
	
	public void integers(){
		int a = 10;
		int b = 20;
	}
	
	public void integersOp(){
		int a  = 10;
		int b  = 20;
		float c = a + b - a * b / a % b;
		int d = a >> 2;
		int e = b >>> 3;
		int f = e <<2;
		float j = a /=2;
		int g = a++;
		int p = b--;
		
	}
	
	
}