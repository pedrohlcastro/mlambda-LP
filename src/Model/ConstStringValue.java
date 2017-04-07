package Model;

public class ConstStringValue extends StringValue{
    private String value;

    public ConstStringValue(String value, int line) {
        super(line);
        this.value = value;
    }

    @Override
    public String value() {
        return this.value;
    }
}
