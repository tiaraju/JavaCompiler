package compiler.generated;
import java_cup.runtime.*;
import compiler.core.*;

%%

%public
%class Scanner
%unicode
%line
%column
%cup
%cupdebug

%{
   StringBuffer string = new StringBuffer();
  
  private Symbol symbol(int type) {
    return new JavaSymbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    return new JavaSymbol(type, yyline+1, yycolumn+1, value);
  }

  private long parseLong(int start, int end, int radix) {
    long result = 0;
    long digit;

    for (int i = start; i < end; i++) {
      digit  = Character.digit(yycharat(i),radix);
      result*= radix;
      result+= digit;
    }

    return result;
  }
%}

/* identifiers */
Identifier = [:jletter:][:jletterdigit:]*	

/* white spaces*/
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* numeric */
IntegerLiteral = 0 | [1-9][0-9]*

/* floats */
FloatLiteral = {IntegerLiteral}"."{IntegerLiteral}

/* strings */
Marker = [\"]
Other_Symbols = \*|\+|\[|\]|\!|\£|\$|\%|\&|\=|\?|\^|\-|\°|\#|\@|\:|\(|\)
Separators = \r|\n|\r\n\t\f
Alphanumerics_ = [ a-zA-Z0-9_]

temp = [ \*|\+|\[|\]|\!|\£|\$|\%|\&|\=|\?|\^|\-|\°|\#|\@|\:|\(|\)|\"|\r|\n|\r\n\t\f a-zA-Z0-9_]*

StringLiteral = {Marker}   {temp}   {Marker}
StringContent =  {Alphanumerics_}*StringContent | {Other_Symbols}*StringContent | {Separators}*StringContent /*Fixme: Doesn't work, it obtains stack overflow*/

/* comments */
Comment = "/**" ( [^*] | \*+ [^/*] )* "*"+ "/"
    
%%

<YYINITIAL> {

  /* keywords */
  "abstract"                     { return symbol(sym.ABSTRACT); }
  "boolean"                      { return symbol(sym.BOOLEAN); }
  "break"                        { return symbol(sym.BREAK); }
  "byte"                         { return symbol(sym.BYTE); }
  "case"                         { return symbol(sym.CASE); }
  "catch"                        { return symbol(sym.CATCH); }
  "char"                         { return symbol(sym.CHAR); }
  "class"                        { return symbol(sym.CLASS); }
  "continue"                     { return symbol(sym.CONTINUE); }
  "default"                      { return symbol(sym.DEFAULT); }
  "do"                           { return symbol(sym.DO); }
  "double"                       { return symbol(sym.DOUBLE); }
  "else"                         { return symbol(sym.ELSE); }
  "extends"                      { return symbol(sym.EXTENDS); }
  "false"						           { return symbol(sym.FALSE);}
  "final"                        { return symbol(sym.FINAL); }
  "finally"                      { return symbol(sym.FINALLY); }
  "float"                        { return symbol(sym.FLOAT); }
  "for"                          { return symbol(sym.FOR); }
  "if"                           { return symbol(sym.IF); }
  "implements"                   { return symbol(sym.IMPLEMENTS); }
  "import"                       { return symbol(sym.IMPORT); }
  "instanceof"                   { return symbol(sym.INSTANCEOF); }
  "int"                          { return symbol(sym.INT); }
  "interface"                    { return symbol(sym.INTERFACE); }
  "long"                         { return symbol(sym.LONG); }
  "native"                       { return symbol(sym.NATIVE); }
  "new"                          { return symbol(sym.NEW); }
  "null"                         { return symbol(sym.NULL); }
  "package"                      { return symbol(sym.PACKAGE); }
  "private"                      { return symbol(sym.PRIVATE); }
  "protected"                    { return symbol(sym.PROTECTED); }
  "public"                       { return symbol(sym.PUBLIC); }
  "return"                       { return symbol(sym.RETURN); }
  "short"                        { return symbol(sym.SHORT); }
  "static"                       { return symbol(sym.STATIC); }
  "super"                        { return symbol(sym.SUPER); }
  "switch"                       { return symbol(sym.SWITCH); }
  "synchronized"                 { return symbol(sym.SYNCHRONIZED); }
  "this"                         { return symbol(sym.THIS); }
  "threadsafe"					 { return symbol(sym.THREADSAFE);}
  "throw"                        { return symbol(sym.THROW); }
  "transient"                    { return symbol(sym.TRANSIENT); }
  "true"						 { return symbol(sym.TRUE);}
  "try"                          { return symbol(sym.TRY); }
  "void"                         { return symbol(sym.VOID); }
  "while"                        { return symbol(sym.WHILE); }
  
  /* check how to consider those later
  "x"							 { return symbol(sym.X);}
  "d"							 { return symbol(sym.D);}
  "e"							 { return symbol(sym.E);}
  "f"							 { return symbol(sym.F);}
  "l"							 { return symbol(sym.L);}
  */
       
/* Identifier*/
  {Identifier} 					 { return symbol(sym.IDENTIFIER,yytext());}

/* Float literals */  
  {FloatLiteral} 				 { return symbol(sym.FLOATING_POINT_LITERAL, new String(yytext()));}

/* Integer literals */  
  {IntegerLiteral}               { return symbol(sym.INTEGER_LITERAL, new String(yytext()));}

/* character literal */
  \'                             { return symbol(sym.CHARLITERAL); }
  
/* Comments*/
  {Comment}                      { /* just ignore it */ }
  
/* separators */
  "("                            { return symbol(sym.LPAREN); }
  ")"                            { return symbol(sym.RPAREN); }
  "{"                            { return symbol(sym.LBRACE); }
  "}"                            { return symbol(sym.RBRACE); }
  "["                            { return symbol(sym.LBRACK); }
  "]"                            { return symbol(sym.RBRACK); }
  ";"                            { return symbol(sym.SEMICOLON); }
  ","                            { return symbol(sym.COMMA); }
  "."   		  				 { return symbol(sym.DOT); }
  
 /* TODO string literal */
  {StringLiteral}                { return symbol(sym.STRING_LITERAL,new String(yytext())); }
  
 /* White spaces */
  {WhiteSpace}					 { /* just ignore it*/}
   
 /* attr operator */
 	"="							 {return symbol(sym.EQ);}
 
/* arithmetical operators*/
"+" 							 {return symbol(sym.PLUS);}
"-" 							 {return symbol(sym.MINUS);}
"*" 							 {return symbol(sym.MULT);}
"/"							     {return symbol(sym.DIV);}
"++"							 {return symbol(sym.PLUSPLUS);}
"+="							 {return symbol(sym.PLUSEQ);}
"-="							 {return symbol(sym.MINUSEQ);}
"*="						     {return symbol(sym.MULTEQ);}
"/="				             {return symbol(sym.DIVEQ);}
"--"							 {return symbol(sym.MINUSMINUS);}
"%"							     {return symbol(sym.MOD);}
"<<"							 {return symbol(sym.LSHIFT);}
">>"							 {return symbol(sym.RSHIFT);}
">>>"							 {return symbol(sym.URSHIFT);}


 /* Logical Operators*/
 "=="							 {return symbol(sym.EQEQ);}
 ">="							 {return symbol(sym.GTEQ);}
 "<="							 {return symbol(sym.LTEQ);}
 "<"							 {return symbol(sym.LT);}
 ">"							 {return symbol(sym.GT);}
 "||"							 {return symbol(sym.OROR);}
 "&&"							 {return symbol(sym.ANDAND);}
 "&"							 {return symbol(sym.AND);}
 "!"							 {return symbol(sym.NOT);}
 "!="							 {return symbol(sym.NOTEQ);}
 "|"							 {return symbol(sym.OR);}
 "&="							 {return symbol(sym.ANDEQ);}
 "|="							 {return symbol(sym.OREQ);}
 "^"						     {return symbol(sym.XOR);}
 "^="                            {return symbol(sym.XOREQ);}
 ">>="							 {return symbol(sym.RSHIFTEQ);}
 "<<="							 {return symbol(sym.LSHIFTEQ);}
 
 

 }