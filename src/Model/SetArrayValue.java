package Model;

public class SetArrayValue extends ArrayValue{
    private Value<?> array;
    private Value<?> index;
    private Value<?> value;

    public SetArrayValue(Value<?> array, Value<?> index, Value<?> value, int line) {
        super(line);
        this.array = array;
        this.index = index;
        this.value = value;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        Value<?> value = (this.value instanceof Variable) ? ((Variable) this.value).value() : this.value;
        Value<?> index = (this.index instanceof Variable) ? ((Variable) this.index).value() : this.index;
        Integer i = 0;
        Integer x = 0;
        Array a = null;
        try{
            a = (Array) array.value();
            i = (Integer)index.value();
            x = (Integer)value.value();
        }
        catch(Exception e){
            System.err.println("[UNABLE TO SET THIS VALUE IN THIS POSITION] LINE - " + super.getLine());
            System.exit(0);
        }
        if(i >= a.size()){
            System.err.println("[SEGMENTATION FAULT] LINE - " + super.getLine());
            System.exit(0);
        }
        a.set(i, x);
        return a;
    }
    
    
}
