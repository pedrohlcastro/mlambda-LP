package Model;

public class FillArrayValue extends ArrayValue{
    private Value<?> size,value;
    public FillArrayValue (Value<?> size, Value<?> value, int line){
        super(line);
        this.value = value;
        this.size = size;
    }
    
    @Override
    public Array value() {
        Value<?> size = (this.size instanceof Variable) ? ((Variable) this.size).value() : this.size;
        Value<?> value = (this.value instanceof Variable) ? ((Variable) this.value).value() : this.value;
        Array a = null;
        if(size instanceof IntValue){
            IntValue v = (IntValue)size;
            a = new Array(v.value());
            if(value instanceof IntValue){
                for(int i=0; i < v.value(); i++){
                    a.set(i, ((IntValue)value).value());
                }
            }
            else{
                System.err.println("[UNKNOWN VALUE FOR ARRAY] LINE - " + super.getLine());
                System.exit(0);
            }
        }
        else{
           System.err.println("[UNKNOWN SIZE FOR ARRAY] LINE - " + super.getLine());
           System.exit(0);
        }
        return a;
    }
    
}
