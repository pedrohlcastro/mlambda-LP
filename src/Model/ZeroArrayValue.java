package Model;

public class ZeroArrayValue extends ArrayValue{
    private Value<?> size;
    
    public ZeroArrayValue(Value<?> size, int line) {
        super(line);
        this.size = size;
    }

    @Override
    public Array value() {
        return null;
    }
    
}
