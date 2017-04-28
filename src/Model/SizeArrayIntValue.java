package Model;

public class SizeArrayIntValue extends IntArrayValue{
    protected Value<?> array;

    public SizeArrayIntValue(Value<?> array, int line) {
        super(array, line);
        this.array = array;
    }

    @Override
    public Integer value(){
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        Array a = null;
        try{
            a = (Array) array.value();
        }
        catch(Exception e){
            System.err.println("[ARRAY NOT DEFINED] LINE - " + super.getLine());
            System.exit(0);
        }
        return a.size();
    };
    
}
