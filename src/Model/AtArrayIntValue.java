package Model;

public class AtArrayIntValue extends IntArrayValue{
    private Value<?> index;

    public AtArrayIntValue(Value<?> index, Value<?> array, int line) {
        super(array, line);
        this.index = index;
    }

    @Override
    public Integer value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        Value<?> index = (this.index instanceof Variable) ? ((Variable) this.index).value() : this.index;
        Array a = null;
        int i = 0;
        try{
            a = (Array) array.value();
            i = ((IntValue) index).value();
        }
        catch(Exception e){
            System.err.println("[UNABLE TO ACCESS THIS POSITION OR ARRAY] LINE - " + super.getLine());
            System.exit(0);
        }
        
        i = a.at(i);
        return i;
    }
    
}
