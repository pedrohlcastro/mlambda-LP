package Model;

public class CompareBoolValue extends BoolValue{
    private RelOp op;
    private Value<?> left;
    private Value<?> right;

    public CompareBoolValue(RelOp op, Value<?> left, Value<?> right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean value() {
        Value<?> left = (this.left instanceof Variable) ? ((Variable) this.left).value() : this.left;
        Value<?> right = (this.right instanceof Variable) ? ((Variable) this.right).value() : this.right;
        switch(this.op){
            case Equal:
                return (Integer)left.value() == (Integer)right.value();
            case NotEqual:
                return (Integer)left.value() != (Integer)right.value();
            case LowerThan:
                return (Integer)left.value() < (Integer)right.value();
            case LowerEqual:
                return (Integer)left.value() <= (Integer)right.value();
            case GreaterThan:
                return (Integer)left.value() > (Integer)right.value();
            case GreaterEqual:
                return (Integer)left.value() >= (Integer)right.value();

        }
        return null;
    }
    
    
}
