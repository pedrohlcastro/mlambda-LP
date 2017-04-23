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
        Array a = (Array) array.value();
        return a.size();
    };
    
}
