package Model;

public class ConstIntValue extends IntValue {
    private int value;

    public ConstIntValue(int value, int line) {
        super(line);
        this.value = value;
    }

    @Override
    public Integer value() {
        return null;
    }
    
    
}
