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
        //System.out.printf("Executando: %s\n", this.value);
        String text = "";
        
        //Value<?> value = (this.value instanceof Variable) ? ((Variable) this.value).value() : this.value;
        Value<?> value = this.value;
        if (value instanceof Variable){
           value = ((Variable) value).value();
            //       System.out.printf("Executando %s\n", value);
        }
        
        if (value instanceof StringValue){
            StringValue sv = (StringValue) value;
            text = sv.value();
        }
        else if (value instanceof IntValue){
            IntValue iv = (IntValue) value;
            int n = iv.value();
            text = "" + n;
        }
        else if (value instanceof ArrayValue){
            ArrayValue av = (ArrayValue) value;
            
        }
        else{
            // erro semantico
        }
        System.out.print(text);
        if (newline)
            System.out.println();
    }
}
