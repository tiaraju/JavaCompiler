
public class SemanticTest{
	
	int a = 10;
	
	public int teste(){
		int a = 10 + 6;
		return 10;
	}
	
	public int testeExpression(){
		int a = 10 + 6 + (8-9*568 - (9+34*6) - (100/5));
		return a;
	}
	
	public int teste(int a){
		return 2;
	}
	public int teste(int c, int d){return 10;}
	
	public void testExistingType(){
		SemanticTest t;
	}
	
	public void testVariable(){
		int a = 10;
		int b = 10;
	}
	
	public void testDeclaration(){
		a = 10;
	}
	
	public boolean testReturnBoolean(){
		return a > 24;
	}
	
	public long testCoercion(){
		return 1;
	}
	
	public void testRelational(){
		boolean a = 10 <= 9;
		boolean b = 10 < 9;
		boolean c = 10 > 9;
		boolean d = 10 >= 9;
		boolean e = 10 == 9;
		boolean f = 10 != 10;
	}
	
	public void testIf(){
		if(10<9){
			int a = 10;
		}
		testRelational();
	}
	
	public void testIfElse(){
		if(10<29){
			a= 10;
		}else{
			a = 25;
		}
	}
	
	public String testReturnString(){
		String e = "teste";
		String ef = "teste";
		ef=e;
		return "a";
	}
	
	public void testCallFunction(){
		boolean bre;
		bre = testReturnBoolean();
	}
}