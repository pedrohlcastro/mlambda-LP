package Model;

public abstract class Value<T> {
    private int line;

    public Value (int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }
    
    public abstract T value();
}
