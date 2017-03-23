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
            this.showError();
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
    private void procWhile () throws IOException{
        this.matchToken (TokenType.WHILE);
        this.procBoolExpr();
        this.matchToken (TokenType.CBRA_OPEN);
        this.procStatements();
        this.matchToken (TokenType.CBRA_CLOSE);
    }
    
    // <text> ::= (<string> | <expr>) { ‘.’ (<string> | <expr>) }
    private void procText (){
        //todo
    }
    
    // <boolexpr> ::= <expr> <boolop> <expr> { (and | or) <boolexpr> }
    private void procBoolExpr() throws IOException{
        this.procExpr();
        this.procBoolOp();
        this.procExpr();
        
        while (this.current.type == TokenType.AND || this.current.type == TokenType.OR){
            if (this.current.type == TokenType.AND)
                this.matchToken (TokenType.AND);
            else if (this.current.type == TokenType.OR)
                this.matchToken (TokenType.OR);
            
            this.procBoolExpr();
        }
    }
    
    //<boolop> ::= '==' | '!=' | '<' | '>' | '<=' | '>='
    private void procBoolOp(){
    
    }
    
    //<expr> ::= <term> [ ('+' | '-') <term> ]
    private void procExpr() throws IOException{
        this.procTerm();
        if (this.current.type == TokenType.PLUS){
            this.matchToken (TokenType.PLUS);
            this.procTerm();
        }
        else if (this.current.type == TokenType.MINUS){
            this.matchToken(TokenType.MINUS);
            this.procTerm();
        }
        else {
            this.showError();
        }   
    }
    
    // <term> ::= <factor> [ ('*' | '/' | '%') <factor> ]
    private void procTerm (){
    
    }
    
    //<factor> ::= [‘+’ | ‘-‘] <number> | <load> | <value> | '(' <expr> ')'
    private void procFactor() throws IOException{     
        if (this.current.type == TokenType.PLUS){
            this.matchToken(TokenType.PLUS);
            this.matchToken(TokenType.NUMBER);
        }
        else if (this.current.type == TokenType.MINUS){
            this.matchToken(TokenType.MINUS);
            this.matchToken(TokenType.NUMBER);
        }
        else if (this.current.type == TokenType.LOAD){
            this.matchToken(TokenType.LOAD);
        }
        else if (this.current.type == TokenType.NEW){
            this.procValue();
        }
        else if (this.current.type == TokenType.VAR){
            this.procValue();
        }
        else if (this.current.type == TokenType.PAR_OPEN){
            this.matchToken(TokenType.PAR_OPEN);
            this.procExpr();
            this.matchToken(TokenType.PAR_CLOSE);
        }
        else {
            this.showError();
        }
        
    }
    
    // <load> ::= load '(' <text> ')'
    private void procLoad () throws IOException {
        this.matchToken (TokenType.LOAD);
        this.matchToken (TokenType.PAR_OPEN);
        this.procText();
        this.matchToken (TokenType.PAR_CLOSE);
    }

    //<value> ::= (<new> | <var>) { '.' <array> } [ '.' <int> ]
    private void procValue(){
        
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
    
    //<nzero> ::= zero '[' <expr> ']'
    private void procNzero() throws IOException{
        this.matchToken (TokenType.ZERO);
        this.matchToken (TokenType.SBRA_OPEN);
        this.procExpr();
        this.matchToken (TokenType.SBRA_CLOSE);
    }
    
    //<nrand> ::= rand '[' <expr> ']'
    private void procNrand(){
        
    }
    
    //<nfill> ::= fill '[' <expr> ',' <expr> ']'
    private void procNfill () throws IOException{
        this.matchToken (TokenType.FILL);
        this.matchToken (TokenType.SBRA_OPEN);
        this.procExpr();
        this.matchToken (TokenType.COMMA);
        this.procExpr();
        this.matchToken (TokenType.SBRA_CLOSE);
    }

    //<array> ::= <show> | <sort> | <add> | <set> | <filter> | <remove> | <each> | <apply>
    private void procArray (){
        
    }
    
    //<show> ::= show '(' ')'
    private void procShow () throws IOException{
        this.matchToken (TokenType.SHOW);
        this.matchToken (TokenType.PAR_OPEN);
        this.matchToken (TokenType.PAR_CLOSE);        
    }
    
    //<sort> ::= sort '(' ')'
    private void procSort(){
        
    }
    
    //<add> ::= add '(' <expr> ')'
    private void procAdd () throws IOException{
        this.matchToken (TokenType.ADD);
        this.matchToken (TokenType.PAR_OPEN);
        this.procExpr();
        this.matchToken (TokenType.PAR_CLOSE);
    }
    
    //<set> ::= set '(' <expr> ',' <expr> ')'
    private void procSet(){
        
    }
    
    //<filter> ::= filter '(' <var> '->' <boolexpr> ')'
    private void procFilter () throws IOException{
        this.matchToken (TokenType.FILTER);
        this.matchToken (TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken (TokenType.ARROW);
        this.procBoolExpr();
        this.matchToken (TokenType.PAR_CLOSE);
    }
    
    //<remove> ::= remove '(' <var> '->' <boolexpr> ')'
    private void procRemove (){
        
    }
    
    //<each> ::= each '(' <var> '->' <statements> ')'
    private void procEach () throws IOException{
        this.matchToken (TokenType.EACH);
        this.matchToken (TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken (TokenType.ARROW);
        this.procStatements();
        this.matchToken (TokenType.PAR_CLOSE);
    }
    
    //<apply> ::= apply '(' <var> '->' <statements> ')'
    private void procApply (){
        
    }
    
    //<int> ::= <at> | <size>
    private void procInt() throws IOException{
        if (this.current.type == TokenType.AT)
            this.procAt();
        else if (this.current.type == TokenType.SIZE)
            this.procSize();
        else
            this.showError();
    }
    
    //<at> ::= at '(' <expr> ')'
    private void procAt() throws IOException{
    }
    
    //<size> ::= size '(' ')'
    private void procSize() throws IOException{
        this.matchToken (TokenType.SIZE);
        this.matchToken (TokenType.PAR_OPEN);
        this.matchToken (TokenType.PAR_CLOSE);
    }

    private void procVar() throws IOException {
        this.matchToken(TokenType.VAR);
    }
}
