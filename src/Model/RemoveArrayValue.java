package Model;

public class RemoveArrayValue extends ArrayValue{
    private Value<?> array;
    private Variable var;
    private BoolValue cond;

    public RemoveArrayValue(Value<?> array, Variable var, BoolValue cond, int line) {
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
