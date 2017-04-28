package Model;

public class AddArrayValue extends ArrayValue {
    private Value<?> array;
    private Value<?> value;

    public AddArrayValue(Value<?> array,Value<?> value, int line) {
        super(line);
        this.array = array;
        this.value = value;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        Value<?> value = (this.value instanceof Variable) ? ((Variable) this.value).value() : this.value;
        Array a = null;
        try{
            a = (Array) array.value();
        }
        catch(Exception e){
            System.err.println("[ARRAY NOT DEFINED] LINE - " + super.getLine());
            System.exit(0);
        }
        
        Array ret = null;
        if(value.value() instanceof Array){
            ret = a.add((Array)value.value());
            return ret;
        }
        if(value instanceof IntValue){
            ret = a.add(((IntValue)value).value());
            return ret;
        }
        System.err.println("[ARRAY NOT DEFINED] LINE - " + super.getLine());
            System.exit(0);
        
        return null;
    }
}
