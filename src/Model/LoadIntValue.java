package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadIntValue extends IntValue{
    private Value<?> text;

    public LoadIntValue(Value<?> text, int line) {
        super(line);
        this.text = text;
    }

    @Override
    public Integer value() {
        Scanner sc = new Scanner(System.in);
        System.out.print(text.value().toString());

        int input = 0;
        try {
            input = sc.nextInt();
        }
        catch (Exception e) {
            System.out.println("Entrada Invalida!");
            System.out.println("Erro: " + e);
        }

        return input;
    }
    
    
}
