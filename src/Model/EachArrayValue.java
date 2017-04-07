package Model;

public class EachArrayValue extends ArrayValue{
    private Value<?> array;
    private Variable var;
    private Command cmd;

    public EachArrayValue(Value<?> array, Variable var, Command cmd, int line) {
        super(line);
        this.array = array;
        this.var = var;
        this.cmd = cmd;
    }

    @Override
    public Array value() {
        return null;
    }   
}
