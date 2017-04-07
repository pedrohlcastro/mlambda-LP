package Model;

public class SortArrayValue extends ArrayValue{
    private Value<?> array;

    public SortArrayValue(Value<?> array, int line) {
        super(line);
        this.array = array;
    }

    @Override
    public Array value() {
        return null;
    }
}
