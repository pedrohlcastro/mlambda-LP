package Model;

public class DualIntExpr extends IntValue{
    private IntOp op;
    private Value<?> left;
    private Value<?> right;

    public DualIntExpr(IntOp op, Value<?> left, Value<?> right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Integer value() {
        return null;
    }
}
