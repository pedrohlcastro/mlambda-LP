package Syntatical;
import Lexical.Lexeme;
import Lexical.LexicalAnalysis;
import Lexical.TokenType;
import java.io.IOException;

public class SyntaticalAnalysis {
    private Lexeme current;
    private LexicalAnalysis lex;

    public SyntaticalAnalysis(LexicalAnalysis lex) throws IOException {
        this.lex = lex;
        this.current = lex.nextToken();
    }
    
    private void showError() {
        if (this.current.type == TokenType.UNEXPECTED_EOF)
            this.errorUnexpectedEOF ();
        else
            this.errorUnexpectedToken (this.current.token);
    }
    
    private void errorUnexpectedToken (String token){
        System.out.printf ("%d: %s\n", this.lex.line(), token);
        System.exit(0);
    }
    
    private void errorUnexpectedEOF (){
        System.out.printf ("%d: %s\n", this.lex.line(), TokenType.UNEXPECTED_EOF);
        System.exit(0);
    }
    
    private void matchToken (TokenType type) throws IOException{
        if (type == this.current.type){
            this.current = lex.nextToken();
        }
        else {
            System.out.printf ("Lexema não esperado!");
            System.exit(0);
        }
    }
    
    //<statements> ::= <cmd>  { <cmd> }
    private void procStatements () throws IOException {
        this.procCmd();
        while (this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER
                || this.current.type == TokenType.LOAD || this.current.type == TokenType.NEW || this.current.type == TokenType.VAR
                || this.current.type == TokenType.PAR_OPEN || this.current.type == TokenType.PRINT || this.current.type == TokenType.PRINTLN
                || this.current.type == TokenType.IF || this.current.type == TokenType.WHILE){
            this.procCmd();
        }
        
    }
    
    //<cmd> ::= <assign> | <print> | <if> | <while>
    private void procCmd () throws IOException{
        if (this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER
                || this.current.type == TokenType.LOAD || this.current.type == TokenType.NEW || this.current.type == TokenType.VAR
                || this.current.type == TokenType.PAR_OPEN){
            this.procAssign();
        }
        else if (this.current.type == TokenType.PRINT || this.current.type == TokenType.PRINTLN){
            this.procPrint();
        }
        else if (this.current.type == TokenType.IF){
            this.procIf();
        }
        else if (this.current.type == TokenType.WHILE){
            this.procWhile();
        }
        else {
            this.showError();
        }
    }
    
    //<assign> ::= <expr> [ ':' <var> {‘,’ <var> } ] ';'
    private void procAssign () throws IOException{
        this.procExpr ();
        
        if (this.current.type == TokenType.SEMI_COLON){
            this.matchToken(TokenType.SEMI_COLON);
            this.procVar();
            while (this.current.type == TokenType.COMMA){
                this.matchToken(TokenType.COMMA);
                this.procVar();
            }
        }
        this.matchToken (TokenType.DOT_COMMA);
    }
    
    //<print> ::= (print | println) '(' <text> ')' ';'
    private void procPrint () throws IOException{
        if (this.current.type == TokenType.PRINT){
            this.matchToken (TokenType.PRINT);
        }
        else if (this.current.type == TokenType.PRINTLN){
            matchToken(TokenType.PRINTLN);
        }
        else {
            this.errorUnexpectedToken(this.current.token);
        }
        
        this.matchToken (TokenType.PAR_OPEN);
        this.procText();
        this.matchToken (TokenType.PAR_CLOSE);
        this.matchToken (TokenType.DOT_COMMA);        
    }
    
    // <if> ::= if <boolexpr> '{' <statements> '}' [else '{' <statements> '}']
    private void procIf () throws IOException{
        this.matchToken (TokenType.IF);
        this.procBoolExpr();
        this.matchToken (TokenType.CBRA_OPEN);
        this.procStatements();
        this.matchToken (TokenType.CBRA_CLOSE);
        
        if (this.current.type == TokenType.ELSE){
            this.matchToken (TokenType.ELSE);
            this.matchToken (TokenType.CBRA_OPEN);
            this.procStatements ();
            this.matchToken (TokenType.CBRA_CLOSE);
        }
    }
    
    //<while> ::= while <boolexpr> '{' <statements> '}'
    private void procWhile (){
    
    }
    
    // <text> ::= (<string> | <expr>) { ‘.’ (<string> | <expr>) }
    private void procText (){
        //todo
    }
    
    // <boolexpr> ::= <expr> <boolop> <expr> { (and | or) <boolexpr> }
    private void procBoolExpr(){
    
    }
    
    //<boolop> ::= '==' | '!=' | '<' | '>' | '<=' | '>='
    
    
    
    
    // FORA DE ORDEM
    
    
    
    // <load> ::= load '(' <text> ')'
    private void procLoad () throws IOException {
        this.matchToken (TokenType.LOAD);
        this.matchToken (TokenType.PAR_OPEN);
        this.procText();
        this.matchToken (TokenType.PAR_CLOSE);
    }

    //<value> ::= (<new> | <var>) { '.' <array> } [ . <int> ]
    private void procValue (){
        
    }

    private void procVar() throws IOException {
        this.matchToken(TokenType.VAR);
    }

    private void procExpr() {
        
    }
    
    
         
    //<new> ::= new (<nzero> | <nrand> | <nfill>)
    private void procNew () throws IOException{
        this.matchToken(TokenType.NEW);
        if (this.current.type == TokenType.ZERO){
            this.procNzero();
        }
        else if (this.current.type == TokenType.RAND){
            this.procNrand();
        }
        else if (this.current.type == TokenType.FILL){
            this.procNfill();
        }
        else {
            this.showError ();
        }
    
    } 

    private void procNfill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procNrand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void procNzero() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
