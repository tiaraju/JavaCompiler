package examples;

import java.io.FileReader;


public class Test extends Main implements Serializable{
	/**
	 * teste
	 */
	int variable;
	int[] array;
	char[] charArray;
	
	public Test(){
		this.variable = 10;
	}
	
	protected void begginTest(int b, float c, boolean de){
		boolean a = true;
		while(a){
			a=false;
		}
	}
	
	native void testFor(int a){
		for(int i=0;i<10;i++){
			if(a >8){
				continue;
			}
			if(a==9){
				break;
			}
			a=10;
		}
	}

	public void logicalOP() {
		boolean t = true;
		boolean f = false;
		boolean a = t && f || t != true || t == f && !f;
		boolean b = t ^ f;

	}

	public void nullOp() {
		Object a = null;
		if(a){
			a= null;
		}else{
			a = new Object();
		}
		
		if(a){
			System.out.println("a");
		}
	}

	public void integers() {
		int a = 10;
		int b = 20;
	}
	
	public synchronized int decrement(int c){
		return --c;
	}
	
	public void switch_case(){
		int a = 10;
		switch (a) {
		case 1:
			System.out.println(1);
			break;
		case 10:
			System.out.println(10);
			break;
		default:
			break;
		}
	}
	
	public void try_exception(){
		int a = 10;
		try{
			a = null;
		}catch(Exception e){
			a = 20;
		}finally{
			a = 10;
		}
	}	

	public void chars() {
		char a = 'A';
		char b = 'B';
	}

	
	/** Comment Test. Check that ignores */
	/**
	 * Comment test with symbols 987,) _ ++ _ {} ^ ` and more than one line
	 */

	public void integersOp() {
		int a = 10;
		int b = 20;
		float c = a + b - a * b / a % b;
		int d = a >> 2;
		int e = b >>> 3;
		int f = e << 2;
		float j = a /= 2;
		int g = a++;
		int p = b--;
	}

	public void floats() {
		float a =  1.5f;
		double b = 1.5986;
		float c =  0.5986f;
	}
	
	public void testThreadSafe(){
		
	}

	public void strings(){
		String a = "";
		String b = "test";
		String c = "AaDa";
		String d = "Aa";
		String e = "A12";
		String f = "09";
		String g = "Mauhsuwswijsiks wuhedywghdwujsoqwks dhywgdywqgsuqjsiqjs uwhsdywgduhsiqjsqs**";
		String h = "Mauhsuwswijsiks wuhedywghdwujsoqwks "
				+ "dhywgdywqgsuqjsiqjs uwhsMauhsuwswijsiks wuhedywghdwujsoqwks "
				+ "dhywgdywqgsuqjsiqjs uwhs "
				+ "Mauhsuwswijsiks wuhedywghdwujsoqwks dhywgdywqgsuqjsiqjs uwhs";
		g = "!:_)(*&&*)oiiis";		
	}

}