package Lexical;

import java.util.Map;
import java.util.HashMap;

class SymbolTable {

    private Map<String,TokenType> st;

    public SymbolTable() {
        this.st = new HashMap<>();

        // symbols
        this.st.put(".", TokenType.DOT);  // .
        this.st.put(",", TokenType.COMMA);  //
        this.st.put(":", TokenType.SEMI_COLON);  // :
        this.st.put(";", TokenType.DOT_COMMA);  // ;
        this.st.put("(", TokenType.PAR_OPEN);  // (
        this.st.put(");", TokenType.PAR_CLOSE);  // );
        this.st.put("{", TokenType.CBRA_OPEN);  // {
        this.st.put("}", TokenType.CBRA_CLOSE);  // }
        this.st.put("[", TokenType.SBRA_OPEN);  // [
        this.st.put("]", TokenType.SBRA_CLOSE);  // ]
        this.st.put("->", TokenType.ARROW);  // ->

        // keywords
        this.st.put("print", TokenType.PRINT); // print
        this.st.put("println", TokenType.PRINTLN); // println
        this.st.put("if", TokenType.IF); // if
        this.st.put("else", TokenType.ELSE); // else
        this.st.put("while", TokenType.WHILE); // while
        this.st.put("load", TokenType.LOAD); // load
        this.st.put("new", TokenType.NEW); // new
        this.st.put("zero", TokenType.ZERO); // zero
        this.st.put("rand", TokenType.RAND); // rand
        this.st.put("fill", TokenType.FILL); // fill
        this.st.put("show", TokenType.SHOW); // show
        this.st.put("sort", TokenType.SORT); // sort
        this.st.put("add", TokenType.ADD); // add
        this.st.put("set", TokenType.SET); // set
        this.st.put("filter", TokenType.FILTER); // filter
        this.st.put("remove", TokenType.REMOVE); // remove
        this.st.put("each", TokenType.EACH); // each
        this.st.put("apply", TokenType.APPLY); // apply
        this.st.put("at", TokenType.AT); // at
        this.st.put("size", TokenType.SIZE); // size

        // operators
        this.st.put("&&", TokenType.AND); // and
        this.st.put("|", TokenType.OR); // or
        this.st.put("==", TokenType.EQUAL); // ==
        this.st.put("!=", TokenType.DIFF); // !=
        this.st.put("<", TokenType.LOWER); // <
        this.st.put(">", TokenType.HIGHER); // >
        this.st.put("<=", TokenType.LOWER_EQ); // <=
        this.st.put(">=", TokenType.HIGHER_EQ); // >=
        this.st.put("+", TokenType.PLUS); // +
        this.st.put("-", TokenType.MINUS); // -
        this.st.put("*", TokenType.MUL); // *
        this.st.put("/", TokenType.DIV); // /
        this.st.put("%", TokenType.MOD); // %
    }

    public boolean contains(String token) {
        return this.st.containsKey(token);
    }

    public TokenType find(String token) {
        return this.contains(token) ?
            this.st.get(token) : TokenType.INVALID_TOKEN;
    }
}
