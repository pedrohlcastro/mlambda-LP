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
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        //Value<?> var = (this.var instanceof Variable) ? ((Variable) this.var).value() : this.var;
        Array a = null;
        try{
            a = (Array) array.value();
        }
        catch(Exception e){
            System.err.println("[ARRAY NOT DEFINED] LINE - " + super.getLine());
            System.exit(0);
        }
        Array ret = new Array(0);
        for(int i=0; i<a.size(); i++){
            ConstIntValue c = new ConstIntValue(a.at(i), -1);
            var.setValue(c);
            if(cond.value())
                ret = ret.add(a.at(i));
        }
        return ret;
    }
    
}

