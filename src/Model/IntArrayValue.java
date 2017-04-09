package Model;

public abstract class IntArrayValue extends IntValue{
    protected Value<?> array;

    public IntArrayValue(Value<?> array, int line) {
        super(line);
        this.array = array;
    }

    @Override
    public abstract Integer value();
    
    
}
