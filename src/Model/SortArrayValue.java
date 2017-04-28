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
        Array b = new Array(a.size());
        for(int i=0; i< a.size(); i++){
            b.set(i,a.at(i));
        }
        b = b.sort();
        return b;
    }
}
