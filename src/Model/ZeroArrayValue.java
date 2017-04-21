package Model;

public class ZeroArrayValue extends ArrayValue{
    private Value<?> size;
    
    public ZeroArrayValue(Value<?> size, int line) {
        super(line);
        this.size = size;
    }

    @Override
    public Array value() {
        Value<?> size = (this.size instanceof Variable) ? ((Variable) this.size).value() : this.size;
        Array a = null;
        if(size instanceof IntValue){
            IntValue v = (IntValue)size;
            a = new Array(v.value());
            for(int i=0; i < v.value(); i++){
                a.set(i, 0);
            }
        }
        else{
            System.err.println("UNKNOWN SIZE FOR ARRAY\n");
            System.exit(0);
        }
        
        return a;
    }
    
}
