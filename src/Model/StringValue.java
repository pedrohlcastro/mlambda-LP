package Model;

public abstract class StringValue extends Value<String>{
    
    public StringValue (int line){
        super(line);
    }

    @Override
    public abstract String value();
}
