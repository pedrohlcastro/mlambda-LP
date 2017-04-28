package Model;

public class ShowArrayValue extends ArrayValue{
    public Value<?> array;

    public ShowArrayValue(Value<?> array, int line) {
        super(line);
        this.array = array;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        
        if( array.value() instanceof Array){
            Array a = (Array)array.value();
            a.show();
            return a;
        }
        
        return null;
    }
    
}
