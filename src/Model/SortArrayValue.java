package Model;

public class SortArrayValue extends ArrayValue{
    private Value<?> array;

    public SortArrayValue(Value<?> array, int line) {
        super(line);
        this.array = array;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable) this.array).value() : this.array;
        //Value<?> index = (this.array instanceof Variable) ? ((Variable) this.index).value() : this.index;
        Array a = null;
        try{
            a = (Array) array.value();
        }
        catch(Exception e){
            System.err.println("[ARRAY NOT DEFINED] LINE - " + super.getLine());
            System.exit(0);
        }
        Array b = new Array(a.size());
        for(int i=0; i< a.size(); i++){
            b.set(i,a.at(i));
        }
        b = b.sort();
        return b;
    }
}
