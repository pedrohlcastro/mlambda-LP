package Model;

public abstract class Variable extends Value<?> {
    private String name;
    private Value<?> value;
    
    public Variable (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(Value<?> value) {
        this.value = value;
    }
    
    public Value<?> value(){}
    
}
