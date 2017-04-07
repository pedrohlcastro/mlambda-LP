package Model;

public class FillArrayValue extends ArrayValue{
    
    public FillArrayValue (Value<?> size, Value<?> value, int line){
        super(line);
    }
    
    @Override
    public Array value() {
        return null;
    }
    
}
