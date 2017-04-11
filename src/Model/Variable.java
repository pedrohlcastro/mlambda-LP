package Model;

public abstract class Variable extends Value {
    private String name;
    private Value value;

    public Variable(String name, Value value, int line) {
        super(line);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setValue(Value value){
        this.value = value;
    }
    
    public Value value(){
        return null;
    }
    
}
