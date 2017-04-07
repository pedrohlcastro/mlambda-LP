package Model;

public abstract class BoolValue extends Value<Boolean>{

    public BoolValue(int line) {
        super(line);
    }

    @Override
    public abstract Boolean value();
    
}
