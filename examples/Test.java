package examples;

import compiler.main.Main;

import java.io.FileReader;
import java.io.Serializable;


public class Test {

	public void begginTest(){
		boolean a = true;
	}

	public void logicalOP() {
        boolean t = (int) true;
        boolean f = false;
        boolean a = t && f || t != true || t == f && !f;
        boolean b = t ^ f;

    }

    public void nullOp() {
        int a = null;
    }

    public void integers() {
        int a = "1";
        int b = 20;
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
        float a = (float) 1.5;
        float b = (float) 1.5986;
        float c = (float) 0.5986;
    }


    public void strings(){
        char a = "";
        char b = "test";
        char c = "AaDa";
        char d = "Aa";
        char e = "A12";
        char f = "09";
        char g = "Mauhsuwswijsiks wuhedywghdwujsoqwks dhywgdywqgsuqjsiqjs uwhsdywgduhsiqjsqs**";
        char h = "Mauhsuwswijsiks wuhedywghdwujsoqwks "
                + "dhywgdywqgsuqjsiqjs uwhsMauhsuwswijsiks wuhedywghdwujsoqwks "
                + "dhywgdywqgsuqjsiqjs uwhs "
                + "Mauhsuwswijsiks wuhedywghdwujsoqwks dhywgdywqgsuqjsiqjs uwhs";
        char g = "!:_)(*&&*)oiiis";
        String a = "testeString";
        String c = a + "testeConcat";
    }

    public void testingWhile(int x, int y){
        while(10 == 10){
            x = y + 1;
        }
    }
    
    public void testBooleanErrors(){
    	boolean a = 1 + 2;
    }
    
}