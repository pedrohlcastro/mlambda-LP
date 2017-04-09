package Model;

public class FilterArrayValue extends ArrayValue{
    private Value<?> array;
    private Variable var;
    private BoolValue cond;

    public FilterArrayValue(Value<?> array, Variable var, BoolValue cond, int line) {
        super(line);
        this.array = array;
        this.var = var;
        this.cond = cond;
    }

    @Override
    public Array value() {
        return null;
    }
    
}

