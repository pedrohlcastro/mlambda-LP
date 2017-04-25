package Model;

public class AddArrayValue extends ArrayValue {
    private Value<?> array;
    private Value<?> value;

    public AddArrayValue(Value<?> array,Value<?> value, int line) {
        super(line);
        this.array = array;
        this.value = value;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        Value<?> value = (this.value instanceof Variable) ? ((Variable) this.value).value() : this.value;
        Array a = (Array) array.value();
        Array ret = null;
        if(value.value() instanceof Array){
            ret = a.add((Array)value.value());
        }
        if(value instanceof IntValue){
            ret = a.add(((IntValue)value).value());
        }
        return ret;
    }
}
