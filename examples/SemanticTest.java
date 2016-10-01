
public class SemanticTest{
	
	public int teste(int b){
		return 10;
	}
	
	public int test(int a){
		if (10 < 20){
			teste(20);
		}else{
			teste(30);
		}
		return 10;
	}
}

