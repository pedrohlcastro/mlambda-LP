package Model;

public abstract class IntValue extends Value<Integer>{

    public IntValue(int line) {
        super(line);
    }
    
    @Override
    public abstract Integer value();
}
