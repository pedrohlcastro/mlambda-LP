package Model;

public class SortArrayValue extends ArrayValue{
    private Value<?> array;

    public SortArrayValue(Value<?> array, int line) {
        super(line);
        this.array = array;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        //Value<?> index = (this.array instanceof Variable) ? ((Variable) this.index).value() : this.index;
        Array a = (Array) array.value();
        a = a.sort();
        return a;
    }
}
