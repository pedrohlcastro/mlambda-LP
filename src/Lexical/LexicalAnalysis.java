package Lexical;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class LexicalAnalysis implements AutoCloseable {

    private int line;
    private PushbackInputStream input;
    private SymbolTable st = new SymbolTable();
    public LexicalAnalysis(String filename) throws LexicalException {
        try {
            input = new PushbackInputStream(new FileInputStream(filename));
        } catch (Exception e) {
            throw new LexicalException("Unable to open file");
        }

        line = 1;
    }

    public void close() throws Exception {
        input.close();
    }

    public int line() {
        return this.line;
    }

    // isdigit = character.isDigit(C); <<< em java

    public Lexeme nextToken() throws IOException {
        Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);
        int c, e = 1;
        while (e != 9 && e != 10){
            c = input.read();
            //System.out.printf("[LOG] - %d\n", (int)c);
            switch (e){
                case 1:
                    if (c == -1)
                        e = 10;
                    else if (c == ' ' || c == '\t' || c == '\n' || c == '\r') //terminar
                        e = 1;
                    else if (c == '#')
                        e = 2;
                    else if (Character.isDigit(c)){
                        lex.token += (char) c;
                        e = 3;
                    }
                    else if (c == '!'){
                        lex.token += (char) c;
                        e = 4;
                    }
                    else if(c == '<' || c == '=' || c == '>'){
                        lex.token += (char) c;
                        e = 5;
                    }
                    else if(c == '-' ){
                        lex.token += (char) c;
                        e = 6;
                    }
                    else if(Character.isLetter(c)){
                        lex.token += (char) c;
                        e = 7;
                    }
                    else if (c == '\"'){
                        e = 8;
                    }
                    else if(c == ';' || c == ':' || c == '.' || c == ',' || c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']' || c == '+' || c == '*' || c == '/' || c == '%'){
                        lex.token += (char)c;
                        e = 9;
                    }
                    else {
                        lex.token += (char) c;
                        lex.type = TokenType.INVALID_TOKEN;
                        e = 10;
                    }
                    break;
                case 2:
                    if (c == '\n' || c == '\r')
                        e = 1;
                    break;
                case 3:
                    if (Character.isDigit(c)){
                        lex.token += (char) c;
                    } else {
                        if (c != -1)
                            input.unread(c);
                        e = 10;
                        lex.type = TokenType.NUMBER;
                    }
                    break;
                case 4:
                    if (c == '='){
                        lex.token += (char) c;
                        e = 9;
                    } else {
                        if (c == -1)
                            lex.type = TokenType.UNEXPECTED_EOF;
                        else
                            lex.type = TokenType.INVALID_TOKEN;
                        e = 10;
                    }
                    break;
                case 5: //eu que fiz
                    if(c == '='){
                        lex.token += (char) c;
                    }
                    else{
                        if(c == -1){
                            lex.type = TokenType.UNEXPECTED_EOF;
                        }
                        else{
                            input.unread(c);
                        }
                        
                    }
                    e = 9;
                    break;
                case 6://eu que fiz
                    if(c == '>'){
                        lex.token += (char) c;
                    }
                    else{
                        if(c == -1){
                            lex.type = TokenType.UNEXPECTED_EOF;
                        }
                        else{
                            input.unread(c);
                        }
                        
                    }
                    e = 9;
                    break;
                case 7:
                    if (Character.isDigit(c) || Character.isLetter(c)){
                        lex.token += (char) c;
                    } else {
                        if (c != -1)
                            input.unread(c);
                        e = 9;
                    }
                    break;
                case 8:
                    if (c == '\"'){
                        e = 10;
                        lex.type = TokenType.STRING;
                    } else {
                        if (c == -1){
                            e = 10;
                            lex.type = TokenType.UNEXPECTED_EOF;
                        } else
                            lex.token += (char) c;
                    }
                    break;
             }
         }
         if ( e == 9){
            if (st.contains(lex.token))
                lex.type = st.find(lex.token);
            else
                lex.type = TokenType.VAR;
         }
         // TODO: 
         // Identar outros arquivos.
         // conferir os cases que eu fiz
         

        return lex;
    }
}
