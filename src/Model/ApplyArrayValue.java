package Model;

public class ApplyArrayValue extends ArrayValue{
    private Value<?> array;
    private Variable var;
    private Command cmd;

    public ApplyArrayValue(Value<?> array, Variable var, Command cmd, int line) {
        super(line);
        this.array = array;
        this.var = var;
        this.cmd = cmd;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        //Value<?> var = (this.var instanceof Variable) ? ((Variable) this.var).value() : this.var;
        Array a = (Array) array.value();
        for(int i=0; i<a.size(); i++){
            ConstIntValue c = new ConstIntValue(a.at(i), -1);
            
            var.setValue(c);
            cmd.execute();
            
            if(var.value() instanceof IntValue){
                a.set(i, ((IntValue)var.value()).value());
            }
            
        }
        return a;
    }
    
    
    
}
