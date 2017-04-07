package Model;

public class SetArrayValue extends ArrayValue{
    private Value<?> array;
    private Value<?> index;
    private Value<?> value;

    public SetArrayValue(Value<?> array, Value<?> index, Value<?> value, int line) {
        super(line);
        this.array = array;
        this.index = index;
        this.value = value;
    }

    @Override
    public Array value() {
        return null;
    }
    
    
}
