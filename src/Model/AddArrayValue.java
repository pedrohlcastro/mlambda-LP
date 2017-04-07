package Model;

public class AddArrayValue extends ArrayValue {
    private Value<?> array;

    public AddArrayValue(Value<?> array, int line) {
        super(line);
        this.array = array;
    }

    @Override
    public Array value() {
        return null;
    }
    
    
}
