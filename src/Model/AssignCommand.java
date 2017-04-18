package Model;

import java.util.ArrayList;
import java.util.List;

public class AssignCommand extends Command{
    private List <Variable> vars;
    private Value<?> value;

    public AssignCommand(Value<?> value, int line) {
        super(line);
        this.vars = new ArrayList<Variable>();
        this.value = value;
    }
    public void addVariable (Variable var){
        this.vars.add(var);
    }
    @Override
    public void execute(){
        for (Variable v : vars){
            //System.out.println("CHEGOU AQUI");
            if (this.value instanceof Variable){
                v.setValue(((Variable) this.value).value());
            }
            else {
               // System.out.println("VaR: " + v.getName());
               // System.out.println("Valor: " + this.value);
                v.setValue(this.value);
            }
        }
    }
}
