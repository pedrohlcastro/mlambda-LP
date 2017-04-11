package Model;

public class ShowArrayValue extends ArrayValue{
    Value<?> array;

    public ShowArrayValue(Value<?> array, int line) {
        super(line);
        this.array = array;
    }

    @Override
    public Array value() {
        return null;
    }
    
}
