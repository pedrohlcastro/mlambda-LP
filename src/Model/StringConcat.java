package Model;

public class StringConcat extends StringValue{
    private Value<?> left;
    private Value<?> right;

    public StringConcat(Value<?> left, Value<?> right, int line) {
        super(line);
        this.left = left;
        this.right = right;
    }

    
    @Override
    public String value() {
        return null;
    }
    
    
}
