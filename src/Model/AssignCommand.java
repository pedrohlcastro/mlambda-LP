package Model;

import java.util.List;

public class AssignCommand extends Command{
    private List <Variable> vars;
    private Value<?> value;

    public AssignCommand(Value<?> value, int line) {
        super(line);
        this.value = value;
    }
    public void addVariable (Variable var){
        this.vars.add(var);
    }
    
    @Override
    public void execute(){
    
    }
    
}
