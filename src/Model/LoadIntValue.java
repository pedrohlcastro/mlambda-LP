package Model;

public class LoadIntValue extends IntValue{
    private Value<?> text;

    public LoadIntValue(Value<?> text, int line) {
        super(line);
        this.text = text;
    }

    @Override
    public Integer value() {
        return null; 
    }
    
    
}
