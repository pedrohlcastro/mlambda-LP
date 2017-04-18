package Model;

public class DualBoolExpr extends BoolValue{
    private BoolOp op;
    private BoolValue left;
    private BoolValue right;

    public DualBoolExpr(BoolOp op, BoolValue left, BoolValue right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean value() {
        switch(this.op){
            case And:
                return left.value() && right.value();
            case Or:
                return left.value() || right.value();
        }
        return null;
    }
    
    
}
