package Model;

public class AtArrayIntValue extends IntArrayValue{
    private Value<?> index;

    public AtArrayIntValue(Value<?> index, Value<?> array, int line) {
        super(array, line);
        this.index = index;
    }

    @Override
    public Integer value() {
        return null;
    }
    
}
