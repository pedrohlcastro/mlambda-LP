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
        //Value<?> index = (this.array instanceof Variable) ? ((Variable) this.index).value() : this.index;
        Array a = (Array) array.value();
        Integer i = (Integer) index.value();
        i = a.at(i);
        return i;
    }
    
}
