package Model;

public class SetArrayValue extends ArrayValue{
    private Value<?> array;
    private Value<?> index;
    private Value<?> value;

    public SetArrayValue(Value<?> array, Value<?> index, Value<?> value, int line) {
        super(line);
        this.array = array;
        this.index = index;
        this.value = value;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        //Value<?> value = (this.value instanceof Variable) ? ((Variable) this.value).value() : this.value;
        //Value<?> index = (this.array instanceof Variable) ? ((Variable) this.index).value() : this.index;
        Integer i = (Integer) index.value();
        Array a = (Array) array.value();
        a.set(i, (Integer)value.value());
        return a;
    }
    
    
}
