package Model;

public class PrintCommand extends Command{
    private Value<?> value;
    private boolean newline;

    public PrintCommand(Value <?> value, boolean newline, int line) {
        super(line);
        this.value = value;
        this.newline = newline;
    }

    @Override
    public void execute() {
        String text = "";
        // completar para outros tipos
        if (value instanceof StringValue){
            StringValue sv = (StringValue) value;
            text = sv.value();
        }
        else if (value instanceof IntValue){
            IntValue iv = (IntValue) value;
            text = String.valueOf(iv.value());
        }
        else if (value instanceof ArrayValue){
            ArrayValue av = (ArrayValue) value;
            //text = Array.(av.value());

        }
        else{
            // erro semantico
        }
        System.out.print(text);
        if (newline)
            System.out.println();
    }
    
    
}
