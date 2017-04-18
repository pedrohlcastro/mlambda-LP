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
        //ConstIntValue left = (ConstIntValue) this.left;
        //ConstIntValue right = (ConstIntValue) this.right;
        
        Value<?> left = (this.left instanceof Variable) ? ((Variable) this.left).value() : this.left;
        Value<?> right = (this.right instanceof Variable) ? ((Variable) this.right).value() : this.right;
        
        if (op == IntOp.Add){
            value = ((Integer)left.value()) + ((Integer)right.value());
        }
        else if(op == IntOp.Sub){
            value = ((Integer)left.value())- ((Integer)right.value());
        }
        else if(op == IntOp.Div){
            value = ((Integer)left.value()) / ((Integer)right.value());
        }
        else if(op == IntOp.Mod){
            value = ((Integer)left.value()) % ((Integer)right.value());
        }
        else if(op == IntOp.Mul){
            value = ((Integer)left.value()) * ((Integer)right.value());
        }
        return value;
    }
}
