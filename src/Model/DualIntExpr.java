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
        int value = 0;
        ConstIntValue left = (ConstIntValue) this.left;
        ConstIntValue right = (ConstIntValue) this.right;
        if (op == IntOp.Add){
            value = left.value() + right.value();
        }
        else if(op == IntOp.Sub){
            value = left.value() - right.value();
        }
        else if(op == IntOp.Div){
            value = left.value() / right.value();
        }
        else if(op == IntOp.Mod){
            value = left.value() % right.value();
        }
        else if(op == IntOp.Mul){
            value = left.value() * right.value();
        }
        return value;
    }
}
