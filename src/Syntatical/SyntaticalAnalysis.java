package Syntatical;
import Lexical.Lexeme;
import Lexical.LexicalAnalysis;
import Lexical.TokenType;
import java.io.IOException;
import Model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            c = this.procAssign();
        }
        else if (this.current.type == TokenType.PRINT || this.current.type == TokenType.PRINTLN){
            c = this.procPrint();
        }
        else if (this.current.type == TokenType.IF){
            c = this.procIf();
        }
        else if (this.current.type == TokenType.WHILE){
            c = this.procWhile();
        }
        else {
            this.showError();
        }
        return c;
    }
    
    //<assign> ::= <expr> [ ':' <var> {‘,’ <var> } ] ';'
    private AssignCommand procAssign () throws IOException{
        Value<?> value = this.procExpr();
        int line = this.lex.line();
        AssignCommand ac = new AssignCommand(value, line);
        Variable v;
        if (this.current.type == TokenType.SEMI_COLON){
            this.matchToken(TokenType.SEMI_COLON);
            v = this.procVar();
            ac.addVariable(v);
            while (this.current.type == TokenType.COMMA){
                this.matchToken(TokenType.COMMA);
                v = this.procVar();
                ac.addVariable(v);
            }

        }

        this.matchToken (TokenType.DOT_COMMA);
        return ac;

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
        IfCommand ic;
        Command c,c1;
        int line = this.lex.line();
        this.matchToken (TokenType.IF);
        BoolValue bv = this.procBoolExpr();
        this.matchToken (TokenType.CBRA_OPEN);
        c = this.procStatements();
        this.matchToken (TokenType.CBRA_CLOSE);
        
        if (this.current.type == TokenType.ELSE){
            this.matchToken (TokenType.ELSE);
            this.matchToken (TokenType.CBRA_OPEN);
            c1 = this.procStatements ();
            this.matchToken (TokenType.CBRA_CLOSE);
            ic = new IfCommand(bv, c, c1, line);
            return ic;
        }
        ic = new IfCommand(bv, c, line);
        return ic;
    }
    
    
    //<while> ::= while <boolexpr> '{' <statements> '}'
    private WhileCommand procWhile () throws IOException{
        int line = this.lex.line();
        this.matchToken (TokenType.WHILE);
        BoolValue bv = this.procBoolExpr();
        this.matchToken (TokenType.CBRA_OPEN);
        Command c = this.procStatements();
        this.matchToken (TokenType.CBRA_CLOSE);
        WhileCommand wc =  new WhileCommand(bv,c,line);
        return wc;
    }
    
    // <text> ::= (<string> | <expr>) { ‘,’ (<string> | <expr>) }
    //    private StringValue procText () throws IOException{
    private Value<?> procText () throws IOException{
        int line = lex.line();
        Value <?> v1 = null;
        Value <?> v2 = null;
        StringConcat sc = null;
        if(this.current.type == TokenType.STRING){
            v1 = this.procString();
        }
        else if(this.current.type == TokenType.VAR || this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER){
            v1 = this.procExpr();
        }
        else{
            this.showError();
        }
        //devia ser recursivo
        while(this.current.type == TokenType.COMMA){
            this.matchToken(TokenType.COMMA);
//            if(this.current.type == TokenType.STRING){
//                v2 = this.procString();
//                sc = new StringConcat(v1, v2, line);
//            }
//            else if(this.current.type == TokenType.VAR || this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER){
//                v2 = this.procExpr();
//                sc = new StringConcat(v1, v2, line);
//            }
//            else{
//                this.showError();
//            }
            v2 = this.procText();
            sc = new StringConcat(v1, v2, line);
            return sc;
        }
        return v1;
    }
    
    // <boolexpr> ::= <expr>    <boolop> <expr> { (and | or) <boolexpr> }
    private BoolValue procBoolExpr() throws IOException{
        Value<?> v1 = this.procExpr();
        RelOp ro = this.procBoolOp();
        Value<?> v2 = this.procExpr();
        BoolOp bo = null;
        DualBoolExpr dbe = null;
        int line = this.lex.line();
        CompareBoolValue cbv = new CompareBoolValue(ro,v1,v2,line);

        while (this.current.type == TokenType.AND || this.current.type == TokenType.OR){
            
            if (this.current.type == TokenType.AND){
                this.matchToken (TokenType.AND);
                bo = BoolOp.And;
            }
            else if (this.current.type == TokenType.OR){
                this.matchToken (TokenType.OR);
                bo = BoolOp.Or;
            }
            
            BoolValue bv = this.procBoolExpr();
            dbe= new DualBoolExpr(bo, cbv, bv, line);
            return dbe;
        }
        return cbv;
    }
    
    //<boolop> ::= '==' | '!=' | '<' | '>' | '<=' | '>='
    private RelOp procBoolOp() throws IOException{
        RelOp ro = null;
        if(this.current.type == TokenType.EQUAL){
            this.matchToken(TokenType.EQUAL);
            ro = RelOp.Equal;
        }
        else if(this.current.type == TokenType.DIFF){
            this.matchToken(TokenType.DIFF);
            ro = RelOp.NotEqual;
        }
        else if(this.current.type == TokenType.LOWER){
            this.matchToken(TokenType.LOWER);
            ro = RelOp.LowerThan;
        }
        else if(this.current.type == TokenType.HIGHER){
            this.matchToken(TokenType.HIGHER);
            ro = RelOp.GreaterThan;
        }
        else if(this.current.type == TokenType.LOWER_EQ){
            this.matchToken(TokenType.LOWER_EQ);
            ro = RelOp.LowerEqual;
        }
        else if(this.current.type == TokenType.HIGHER_EQ){
            this.matchToken(TokenType.HIGHER_EQ);
            ro = RelOp.GreaterEqual;
        }
        else
            this.showError();
        return ro;
    }
    
    //<expr> ::= <term> [ ('+' | '-') <term> ]
    private Value<?> procExpr() throws IOException{
        Value<?> v1 = this.procTerm();
        int line = lex.line();
        if (this.current.type == TokenType.PLUS ||  this.current.type == TokenType.MINUS){
            IntOp io;
            if (this.current.type == TokenType.PLUS) {
                this.matchToken (TokenType.PLUS);
                io = IntOp.Add;
            } else {
                this.matchToken (TokenType.MINUS);
                io = IntOp.Sub;
            }
            Value<?> v2 = this.procTerm();
            
            DualIntExpr die = new DualIntExpr(io, v1, v2, line);
            return die;
        }
        return v1;
    }
    
    // <term> ::= <factor> [ ('*' | '/' | '%') <factor> ]
    private Value<?> procTerm () throws IOException{
        Value<?> v1 = this.procFactor();
        int line = lex.line();
        if(this.current.type == TokenType.MUL || this.current.type == TokenType.DIV || this.current.type == TokenType.MOD){
            IntOp io = null;
            if(this.current.type == TokenType.MUL){
                this.matchToken(TokenType.MUL);
                io = IntOp.Mul;
            }
            else if(this.current.type == TokenType.DIV){
                this.matchToken(TokenType.DIV);
                io = IntOp.Div;
            }
            else if(this.current.type == TokenType.MOD){
                this.matchToken(TokenType.MOD); 
                io = IntOp.Mod;                    
            }
            else{
                this.showError();
            }
            Value<?> v2 = this.procFactor();
            DualIntExpr die = new DualIntExpr(io, v1, v2, line);
            return die;
        }
        return v1;
    }
    
    //<factor> ::= [‘+’ | ‘-‘] <number> | <load> | <value> | '(' <expr> ')'
    private Value<?> procFactor() throws IOException{     
        if (this.current.type == TokenType.PLUS){
            this.matchToken(TokenType.PLUS);
            return this.procNumber();
        }
        else if (this.current.type == TokenType.MINUS){
            this.matchToken(TokenType.MINUS);
            return this.procNumber();
        }
        else if (this.current.type == TokenType.NUMBER){
            return this.procNumber();
        }
        else if (this.current.type == TokenType.LOAD){
            return this.procLoad();
        }
        else if (this.current.type == TokenType.NEW){
            return this.procValue();
        }
        else if (this.current.type == TokenType.VAR){
            return this.procValue();
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
        Value<?> v;
        int line = this.lex.line();
        this.matchToken (TokenType.LOAD);
        this.matchToken (TokenType.PAR_OPEN);
        v = this.procText();
        this.matchToken (TokenType.PAR_CLOSE);
        LoadIntValue ld = new LoadIntValue(v,line);
        return ld;
    }

    //<value> ::= (<new> | <var>) { '.' <array> } [ '.' <int> ]
    private Value<?> procValue() throws IOException{
        Value <?> v = null;
        if(this.current.type == TokenType.NEW){
            v = this.procNew();
        }
        else if(this.current.type == TokenType.VAR){
            // FIXME: me mover pra baixo.
            v = this.procVar();
        }
        while(this.current.type == TokenType.DOT){
            this.matchToken(TokenType.DOT);
            if (current.type == TokenType.AT || current.type == TokenType.SIZE){
                v = this.procInt(v);
                break;
            }
            else 
                v = this.procArray(v);
        }
        return v;
    }
    
    //<new> ::= new (<nzero> | <nrand> | <nfill>)
    private ArrayValue procNew () throws IOException{
        ArrayValue a = null;
        this.matchToken(TokenType.NEW);
        if (this.current.type == TokenType.ZERO){
            a = this.procNzero();
        }
        else if (this.current.type == TokenType.RAND){
            a = this.procNrand();
        }
        else if (this.current.type == TokenType.FILL){
            a = this.procNfill();
        }
        else {
            this.showError ();
        }
        return a;
    }
    
    //<nzero> ::= zero '[' <expr> ']'
    private ZeroArrayValue procNzero() throws IOException{
        Value<?> v;
        int line = this.lex.line();
        this.matchToken (TokenType.ZERO);
        this.matchToken (TokenType.SBRA_OPEN);
        v = this.procExpr();
        this.matchToken (TokenType.SBRA_CLOSE);
        ZeroArrayValue z = new ZeroArrayValue(v, line);
        return z;
    }
    
    //<nrand> ::= rand '[' <expr> ']'
    private RandArrayValue procNrand() throws IOException{
        Value<?> v;
        int line = this.lex.line();
        this.matchToken(TokenType.RAND);
        this.matchToken(TokenType.SBRA_OPEN);
        v = this.procExpr();
        RandArrayValue r = new RandArrayValue(v, line);
        this.matchToken(TokenType.SBRA_CLOSE);
        return r;
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
    private ArrayValue procArray (Value<?> v) throws IOException{
        if(this.current.type == TokenType.SHOW){
            return this.procShow(v);
        }
        else if(this.current.type == TokenType.SORT){
            return this.procSort( v);
        }
        else if(this.current.type == TokenType.ADD){
            return this.procAdd(v);
        }
        else if(this.current.type == TokenType.SET){
            return this.procSet(v);
        }
        else if(this.current.type == TokenType.FILTER){
            return this.procFilter(v);
        }
        else if(this.current.type ==  TokenType.REMOVE){
            return this.procRemove(v);
        }
        else if(this.current.type ==  TokenType.EACH){
            return this.procEach(v);
        }
        else if(this.current.type ==  TokenType.APPLY){
            return this.procApply(v);
        }
        else
            this.showError();
        return null;
    }
    
    //<show> ::= show '(' ')'
    private ShowArrayValue procShow (Value<?> v) throws IOException{
        int line = this.lex.line();
        this.matchToken (TokenType.SHOW);
        this.matchToken (TokenType.PAR_OPEN);
        this.matchToken (TokenType.PAR_CLOSE);  
        ShowArrayValue s = new ShowArrayValue(v,line);
        return s;   
    }
    
    //<sort> ::= sort '(' ')'
    private SortArrayValue procSort(Value<?> v) throws IOException{
        int line = this.lex.line();
        SortArrayValue s = null;
        if(this.current.type == TokenType.SORT){
            this.matchToken(TokenType.SORT);
            this.matchToken(TokenType.PAR_OPEN);
            this.matchToken(TokenType.PAR_CLOSE);
            s = new SortArrayValue(v, line);
        }
        else
            this.showError(); 
        return s;
    }
    
    //<add> ::= add '(' <expr> ')'
    private AddArrayValue procAdd (Value<?> v) throws IOException{
        this.matchToken (TokenType.ADD);
        this.matchToken (TokenType.PAR_OPEN);
        this.procExpr();
        this.matchToken (TokenType.PAR_CLOSE);
        return null;
    }
    
    //<set> ::= set '(' <expr> ',' <expr> ')'
    private SetArrayValue procSet(Value<?> v) throws IOException{
        Value<?> pos = null;
        Value<?> valor = null;
        int line = this.lex.line();
        if(this.current.type == TokenType.SET){
            this.matchToken(TokenType.SET);
            this.matchToken(TokenType.PAR_OPEN);
            if(this.current.type == TokenType.PLUS || this.current.type == TokenType.MINUS || this.current.type == TokenType.NUMBER){
                pos = this.procExpr();
                this.matchToken(TokenType.COMMA);
                valor = this.procExpr();
            }
            else
                this.showError();
            this.matchToken(TokenType.PAR_CLOSE);
        }
        SetArrayValue s = new SetArrayValue(v, pos, valor, line);
        return s;
    }
    
    //<filter> ::= filter '(' <var> '->' <boolexpr> ')'
    private FilterArrayValue procFilter (Value<?> v) throws IOException{
        this.matchToken (TokenType.FILTER);
        this.matchToken (TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken (TokenType.ARROW);
        this.procBoolExpr();
        this.matchToken (TokenType.PAR_CLOSE);
        return null;
    }
    
    //<remove> ::= remove '(' <var> '->' <boolexpr> ')'
    private RemoveArrayValue procRemove (Value<?> v) throws IOException{
        this.matchToken(TokenType.REMOVE);
        this.matchToken(TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken(TokenType.ARROW);
        this.procBoolExpr();
        this.matchToken(TokenType.PAR_CLOSE);
        return null;
    }
    
    //<each> ::= each '(' <var> '->' <statements> ')'
    private EachArrayValue procEach (Value<?> v) throws IOException{
        this.matchToken (TokenType.EACH);
        this.matchToken (TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken (TokenType.ARROW);
        this.procStatements();
        this.matchToken (TokenType.PAR_CLOSE);
        return null;
    }
    
    //<apply> ::= apply '(' <var> '->' <statements> ')'
    private ApplyEachValue procApply (Value<?> v) throws IOException{
        this.matchToken(TokenType.APPLY);
        this.matchToken(TokenType.PAR_OPEN);
        this.procVar();
        this.matchToken(TokenType.ARROW);
        this.procStatements();
        this.matchToken(TokenType.PAR_CLOSE);
        return null;
    }
    
    //<int> ::= <at> | <size>
    private IntValue procInt(Value<?> v) throws IOException{
        if (this.current.type == TokenType.AT)
            return this.procAt(v);
        else if (this.current.type == TokenType.SIZE)
            return this.procSize(v);
        else
            this.showError();
        return null;
    }
    
    //<at> ::= at '(' <expr> ')'
    private AtArrayIntValue procAt(Value<?> v) throws IOException{
        Value<?> v1;
        int line = this.lex.line();
        this.matchToken(TokenType.AT);
        this.matchToken(TokenType.PAR_OPEN);
        v1 = this.procExpr();
        this.matchToken(TokenType.PAR_CLOSE);
        AtArrayIntValue av = new AtArrayIntValue(v1, v, line);
        return av;
    }
    
    //<size> ::= size '(' ')'
    private SizeArrayIntValue procSize(Value<?> v) throws IOException{
        int line = this.lex.line();
        this.matchToken (TokenType.SIZE);
        this.matchToken (TokenType.PAR_OPEN);
        this.matchToken (TokenType.PAR_CLOSE);
        SizeArrayIntValue s = new SizeArrayIntValue(v,line);
        return s;
    }
    
    private Map <String, Variable> vars = new HashMap <String, Variable>();
    private Variable procVar() throws IOException {
        String n = this.current.token;
        this.matchToken(TokenType.VAR);
        Variable v;
        if (vars.containsKey(n))
            v = vars.get(n);
        else {
            v = new Variable (n);
            vars.put(n, v);
        }
        return v;
    }
    
    private ConstIntValue procNumber () throws IOException{
        int line = lex.line();
        String s = current.token;
        this.matchToken(TokenType.NUMBER);
        ConstIntValue civ = new ConstIntValue(Integer.parseInt(s), line);
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
