package Model;

public abstract class ArrayValue extends Value<Array>{

    public ArrayValue(int line) {
        super(line);
    }
    
    @Override
    public abstract Array value();   
}
