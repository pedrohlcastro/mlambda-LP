package Model;

import java.util.Scanner;

public class LoadIntValue extends IntValue{
    private Value<?> text;

    public LoadIntValue(Value<?> text, int line) {
        super(line);
        this.text = text;
        System.out.print(text.value());
    }

    @Override
    public Integer value() {
        Scanner sc = new Scanner(System.in);
        String buffer = sc.nextLine();
        int valor = 0;
        try{
            valor = Integer.parseInt(buffer);
        }
        catch(Exception e){
            System.err.println("UNEXPECTED INPUT TYPE\nEXPECTING A INT");
            System.exit(0);
        }
        return valor;
    }
    
    
}
