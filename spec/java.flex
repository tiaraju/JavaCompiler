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
  "const"                        { return symbol(sym.CONST); }
  "continue"                     { return symbol(sym.CONTINUE); }
  "do"                           { return symbol(sym.DO); }
  "double"                       { return symbol(sym.DOUBLE); }
  "else"                         { return symbol(sym.ELSE); }
  "extends"                      { return symbol(sym.EXTENDS); }
  "final"                        { return symbol(sym.FINAL); }
  "finally"                      { return symbol(sym.FINALLY); }
  "float"                        { return symbol(sym.FLOAT); }
  "for"                          { return symbol(sym.FOR); }
  "default"                      { return symbol(sym.DEFAULT); }
  "implements"                   { return symbol(sym.IMPLEMENTS); }
  "import"                       { return symbol(sym.IMPORT); }
  "instanceof"                   { return symbol(sym.INSTANCEOF); }
  "int"                          { return symbol(sym.INT); }
  "interface"                    { return symbol(sym.INTERFACE); }
  "long"                         { return symbol(sym.LONG); }
  "native"                       { return symbol(sym.NATIVE); }
  "new"                          { return symbol(sym.NEW); }
  "goto"                         { return symbol(sym.GOTO); }
  "if"                           { return symbol(sym.IF); }
  "public"                       { return symbol(sym.PUBLIC); }
  "short"                        { return symbol(sym.SHORT); }
  "super"                        { return symbol(sym.SUPER); }
  "switch"                       { return symbol(sym.SWITCH); }
  "synchronized"                 { return symbol(sym.SYNCHRONIZED); }
  "package"                      { return symbol(sym.PACKAGE); }
  "private"                      { return symbol(sym.PRIVATE); }
  "protected"                    { return symbol(sym.PROTECTED); }
  "transient"                    { return symbol(sym.TRANSIENT); }
  "return"                       { return symbol(sym.RETURN); }
  "void"                         { return symbol(sym.VOID); }
  "static"                       { return symbol(sym.STATIC); }
  "while"                        { return symbol(sym.WHILE); }
  "this"                         { return symbol(sym.THIS); }
  "throw"                        { return symbol(sym.THROW); }
  "throws"                       { return symbol(sym.THROWS); }
  "try"                          { return symbol(sym.TRY); }
  "volatile"                     { return symbol(sym.VOLATILE); }
  "strictfp"                     { return symbol(sym.STRICTFP); }
 }
