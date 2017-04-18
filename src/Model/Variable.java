package Model;

public class Variable extends Value<Value<?>> {
    private String name;
    private Value<?> value;

    public Variable(String name) {
        super(-1);
        this.name = name;
        this.value = null;
    }

    public String getName() {
        return name;
    }

    public void setValue(Value<?> value){
        this.value = value;
    }
    
    public Value<?> value(){        
        return this.value;
    }
}