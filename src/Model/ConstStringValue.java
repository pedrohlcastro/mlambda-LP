package Model;

public class ConstStringValue extends StringValue{
    private String text;

    public ConstStringValue(String text, int line) {
        super(line);
        this.text = text;
    }

    @Override
    public String value() {
        return this.text;
    }
}
