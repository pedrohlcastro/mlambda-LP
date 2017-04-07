package Model;

public class ZeroArrayValue extends ArrayValue{

    public ZeroArrayValue(Value<?> size, int line) {
        super(line);
    }

    @Override
    public Array value() {
        return null;
    }
    
}
