package Model;

public abstract class SizeArrayIntValue extends IntArrayValue{
    protected Value<?> array;

    public SizeArrayIntValue(Value<?> array, int line) {
        super(array, line);
        this.array = array;
    }

    @Override
    public abstract Integer value();
    
}
