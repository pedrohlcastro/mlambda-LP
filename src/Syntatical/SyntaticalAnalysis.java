package Syntatical;
import Lexical.Lexeme;
import Lexical.LexicalAnalysis;
import Lexical.TokenType;
import java.io.IOException;
import Model.*;

public class SyntaticalAnalysis {
    private Lexeme current;
    private LexicalAnalysis lex;

    public SyntaticalAnalysis(LexicalAnalysis lex) throws IOException {
        this.lex = lex;
        this.current = lex.nextToken();
    }
    
    public Command init () throws IOException{
        Command c = this.procStatements();
        this.matchToken(TokenType.END_OF_FILE);
        return c;
    }
    
    private void showError() {
        if (this.current.type == TokenType.UNEXPECTED_EOF || this.current.type == TokenType.END_OF_FILE)
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
            this.showError();
        }
    }
    
    //<statements> ::= <cmd>  { <cmd> }
    private Command procStatements () throws IOException {
        CommandBlock cb = new CommandBlock();
        Command c = this.procCmd();
        cb.addCommand(c);
        while (this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER
                || this.current.type == TokenType.LOAD || this.current.type == TokenType.NEW || this.current.type == TokenType.VAR
                || this.current.type == TokenType.PAR_OPEN || this.current.type == TokenType.PRINT || this.current.type == TokenType.PRINTLN
                || this.current.type == TokenType.IF || this.current.type == TokenType.WHILE){
            c = this.procCmd();
            cb.addCommand(c);
        }
        return cb;
    }
    
    //<cmd> ::= <assign> | <print> | <if> | <while>
    private Command procCmd () throws IOException{
        Command c = null;
        if (this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER
                || this.current.type == TokenType.LOAD || this.current.type == TokenType.NEW || this.current.type == TokenType.VAR
                || this.current.type == TokenType.PAR_OPEN){
            this.procAssign();
        }
        else if (this.current.type == TokenType.PRINT || this.current.type == TokenType.PRINTLN){
            c = this.procPrint();
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
        return c;
    }
    
    //<assign> ::= <expr> [ ':' <var> {‘,’ <var> } ] ';'
    private AssignCommand procAssign () throws IOException{
        this.procExpr();
        
        if (this.current.type == TokenType.SEMI_COLON){
            this.matchToken(TokenType.SEMI_COLON);
            this.procVar();
            while (this.current.type == TokenType.COMMA){
                this.matchToken(TokenType.COMMA);
                this.procVar();
            }
        }
        this.matchToken (TokenType.DOT_COMMA);
        return null;
    }
    
    //<print> ::= (print | println) '(' <text> ')' ';'
    private PrintCommand procPrint () throws IOException{
        int line = lex.line();
        boolean newLine = false;
        if (this.current.type == TokenType.PRINT){
            newLine = false;
            this.matchToken (TokenType.PRINT);
        }
        else if (this.current.type == TokenType.PRINTLN){
            newLine = true;
            this.matchToken(TokenType.PRINTLN);
        }
        else {
            this.showError();
        }
        
        this.matchToken (TokenType.PAR_OPEN);
        Value<?> v = this.procText();
        this.matchToken (TokenType.PAR_CLOSE);
        this.matchToken (TokenType.DOT_COMMA);    
        PrintCommand pc = new PrintCommand(v, newLine, line);
        return pc;    
    }
    
    // <if> ::= if <boolexpr> '{' <statements> '}' [else '{' <statements> '}']
    private IfCommand procIf () throws IOException{
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
        return null;
    }
    
    
    //<while> ::= while <boolexpr> '{' <statements> '}'
    private WhileCommand procWhile () throws IOException{
        this.matchToken (TokenType.WHILE);
        this.procBoolExpr();
        this.matchToken (TokenType.CBRA_OPEN);
        this.procStatements();
        this.matchToken (TokenType.CBRA_CLOSE);
        return null;
    }
    
    // <text> ::= (<string> | <expr>) { ‘.’ (<string> | <expr>) }
    //    private StringValue procText () throws IOException{
    private Value<?> procText () throws IOException{
        Value <?> v;
        if(this.current.type == TokenType.STRING){
            v = this.procString();
            return v;
        }
        else if(this.current.type == TokenType.VAR || this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER){
            v = this.procExpr();
            return v;
        }
        else{
            this.showError();
        }
        
        while(this.current.type == TokenType.COMMA){
            this.matchToken(TokenType.COMMA);
            if(this.current.type == TokenType.STRING){
                this.procString();
            }
            else if(this.current.type == TokenType.VAR || this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER){
                this.procExpr();
            }
            else{
                this.showError();
            }
        }
        return null;
    }
    
    // <boolexpr> ::= <expr> <boolop> <expr> { (and | or) <boolexpr> }
    private BoolValue procBoolExpr() throws IOException{
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
        return null;
    }
    
    //<boolop> ::= '==' | '!=' | '<' | '>' | '<=' | '>='
    private RelOp procBoolOp() throws IOException{
        if(this.current.type == TokenType.EQUAL){
            this.matchToken(TokenType.EQUAL);
        }
        else if(this.current.type == TokenType.DIFF){
            this.matchToken(TokenType.DIFF);
        }
        else if(this.current.type == TokenType.LOWER){
            this.matchToken(TokenType.LOWER);
        }
        else if(this.current.type == TokenType.HIGHER){
            this.matchToken(TokenType.HIGHER);
        }
        else if(this.current.type == TokenType.LOWER_EQ){
            this.matchToken(TokenType.LOWER_EQ);
        }
        else if(this.current.type == TokenType.HIGHER_EQ){
            this.matchToken(TokenType.HIGHER_EQ);
        }
        else
            this.showError();
        return null;
    }
    
    //<expr> ::= <term> [ ('+' | '-') <term> ]
    private Value<?> procExpr() throws IOException{
        this.procTerm();
        if (this.current.type == TokenType.PLUS){
            this.matchToken (TokenType.PLUS);
            this.procTerm();
        }
        else if (this.current.type == TokenType.MINUS){
            this.matchToken(TokenType.MINUS);
            this.procTerm();
        }
        //else {
        //    this.showError();
        //}   
        return null;
    }
    
    // <term> ::= <factor> [ ('*' | '/' | '%') <factor> ]
    private Value<?> procTerm () throws IOException{
        this.procFactor();
        if(this.current.type == TokenType.MUL || this.current.type == TokenType.DIV || this.current.type == TokenType.MOD){
            if(this.current.type == TokenType.MUL)
                this.matchToken(TokenType.MUL);
            else if(this.current.type == TokenType.DIV)
                this.matchToken(TokenType.DIV);
            else if(this.current.type == TokenType.MOD){
                this.matchToken(TokenType.MOD);                     
            }
            else{
                this.showError();
            }
            this.procFactor();

        }
        return null;
    }
    
    //<factor> ::= [‘+’ | ‘-‘] <number> | <load> | <value> | '(' <expr> ')'
    private Value<?> procFactor() throws IOException{     
        if (this.current.type == TokenType.PLUS){
            this.matchToken(TokenType.PLUS);
            this.procNumber();
        }
        else if (this.current.type == TokenType.MINUS){
            this.matchToken(TokenType.MINUS);
            this.procNumber();
        }
        else if (this.current.type == TokenType.NUMBER){
            //matchToken (TokenType.NUMBER);
            this.procNumber();
        }
        else if (this.current.type == TokenType.LOAD){
            this.procLoad();
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
        return null;
    }
    
    // <load> ::= load '(' <text> ')'
    private LoadIntValue procLoad () throws IOException {
        this.matchToken (TokenType.LOAD);
        this.matchToken (TokenType.PAR_OPEN);
        this.procText();
        this.matchToken (TokenType.PAR_CLOSE);
        return null;
    }

    //<value> ::= (<new> | <var>) { '.' <array> } [ '.' <int> ]
    private Value<?> procValue() throws IOException{
        if(this.current.type == TokenType.NEW){
            this.procNew();
        }
        else if(this.current.type == TokenType.VAR){
            this.procVar();
        }
        while(this.current.type == TokenType.DOT){
            this.matchToken(TokenType.DOT);
            if (current.type == TokenType.AT || current.type == TokenType.SIZE){
                this.procInt();
                break;
            }
            else 
                this.procArray();
        }
        return null;
    }
    
    //<new> ::= new (<nzero> | <nrand> | <nfill>)
    private ArrayValue procNew () throws IOException{
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
        return null;
    }
    
    //<nzero> ::= zero '[' <expr> ']'
    private ZeroArrayValue procNzero() throws IOException{
        this.matchToken (TokenType.ZERO);
        this.matchToken (TokenType.SBRA_OPEN);
        this.procExpr();
        this.matchToken (TokenType.SBRA_CLOSE);
        return null;
    }
    
    //<nrand> ::= rand '[' <expr> ']'
    private RandArrayValue procNrand() throws IOException{
        this.matchToken(TokenType.RAND);
        this.matchToken(TokenType.SBRA_OPEN);
        this.procExpr();
        this.matchToken(TokenType.SBRA_CLOSE);
        return null;
    }
    
    //<nfill> ::= fill '[' <expr> ',' <expr> ']'
    private FillArrayValue procNfill () throws IOException{
        this.matchToken (TokenType.FILL);
        this.matchToken (TokenType.SBRA_OPEN);
        this.procExpr();
        this.matchToken (TokenType.COMMA);
        this.procExpr();
        this.matchToken (TokenType.SBRA_CLOSE);
        return null;
    }

    //<array> ::= <show> | <sort> | <add> | <set> | <filter> | <remove> | <each> | <apply>
    private ArrayValue procArray () throws IOException{
        if(this.current.type == TokenType.SHOW){
            this.procShow();
        }
        else if(this.current.type == TokenType.SORT){
            this.procSort();
        }
        else if(this.current.type == TokenType.ADD){
            this.procAdd();
        }
        else if(this.current.type == TokenType.SET){
            this.procSet();
        }
        else if(this.current.type == TokenType.FILTER){
            this.procFilter();
        }
        else if(this.current.type ==  TokenType.REMOVE){
            this.procRemove();
        }
        else if(this.current.type ==  TokenType.EACH){
            this.procEach();
        }
        else if(this.current.type ==  TokenType.APPLY){
            this.procApply();
        }
        else
            this.showError();
        return null;
    }
    
    //<show> ::= show '(' ')'
    private ShowArrayValue procShow () throws IOException{
        this.matchToken (TokenType.SHOW);
        this.matchToken (TokenType.PAR_OPEN);
        this.matchToken (TokenType.PAR_CLOSE);  
        return null;      
    }
    
    //<sort> ::= sort '(' ')'
    private SortArrayValue procSort() throws IOException{
        if(this.current.type == TokenType.SORT){
            this.matchToken(TokenType.SORT);
            this.matchToken(TokenType.PAR_OPEN);
            this.matchToken(TokenType.PAR_CLOSE);
        }
        else
            this.showError();
        
        return null;
    }
    
    //<add> ::= add '(' <expr> ')'
    private AddArrayValue procAdd () throws IOException{
        this.matchToken (TokenType.ADD);
        this.matchToken (TokenType.PAR_OPEN);
        this.procExpr();
        this.matchToken (TokenType.PAR_CLOSE);
        return null;
    }
    
    //<set> ::= set '(' <expr> ',' <expr> ')'
    private SetArrayValue procSet() throws IOException{
        if(this.current.type == TokenType.SET){
            this.matchToken(TokenType.SET);
            this.matchToken(TokenType.PAR_OPEN);
            if(this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER){
                this.procExpr();
                this.matchToken(TokenType.COMMA);
                this.procExpr();
            }
            else
                this.showError();
            this.matchToken(TokenType.PAR_CLOSE);
        }
        return null;
    }
    
    //<filter> ::= filter '(' <var> '->' <boolexpr> ')'
    private FilterArrayValue procFilter () throws IOException{
        this.matchToken (TokenType.FILTER);
        this.matchToken (TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken (TokenType.ARROW);
        this.procBoolExpr();
        this.matchToken (TokenType.PAR_CLOSE);
        return null;
    }
    
    //<remove> ::= remove '(' <var> '->' <boolexpr> ')'
    private RemoveArrayValue procRemove () throws IOException{
        this.matchToken(TokenType.REMOVE);
        this.matchToken(TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken(TokenType.ARROW);
        this.procBoolExpr();
        this.matchToken(TokenType.PAR_CLOSE);
        return null;
    }
    
    //<each> ::= each '(' <var> '->' <statements> ')'
    private EachArrayValue procEach () throws IOException{
        this.matchToken (TokenType.EACH);
        this.matchToken (TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken (TokenType.ARROW);
        this.procStatements();
        this.matchToken (TokenType.PAR_CLOSE);
        return null;
    }
    
    //<apply> ::= apply '(' <var> '->' <statements> ')'
    private ApplyEachValue procApply () throws IOException{
        this.matchToken(TokenType.APPLY);
        this.matchToken(TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken(TokenType.ARROW);
        this.procStatements();
        this.matchToken(TokenType.PAR_CLOSE);
        return null;
    }
    
    //<int> ::= <at> | <size>
    private IntValue procInt() throws IOException{
        if (this.current.type == TokenType.AT)
            this.procAt();
        else if (this.current.type == TokenType.SIZE)
            this.procSize();
        else
            this.showError();
        return null;
    }
    
    //<at> ::= at '(' <expr> ')'
    private AtArrayIntValue procAt() throws IOException{
        this.matchToken(TokenType.AT);
        this.matchToken(TokenType.PAR_OPEN);
        this.procExpr();
        this.matchToken(TokenType.PAR_CLOSE);
        return null;
    }
    
    //<size> ::= size '(' ')'
    private SizeArrayIntValue procSize() throws IOException{
        this.matchToken (TokenType.SIZE);
        this.matchToken (TokenType.PAR_OPEN);
        this.matchToken (TokenType.PAR_CLOSE);
        return null;
    }

    private Variable procVar() throws IOException {
        this.matchToken(TokenType.VAR);
        return null;
    }
    
    private ConstIntValue procNumber () throws IOException{
        int line = lex.line();
        ConstIntValue civ = new ConstIntValue(Integer.parseInt(current.token), line);
        this.matchToken(TokenType.NUMBER);
        return civ;
    }    
    
    private ConstStringValue procString () throws IOException{
        int line = lex.line();
        String text = current.token;
        this.matchToken(TokenType.STRING);
        ConstStringValue csv = new ConstStringValue (text, line);
        return csv;
    }
}
