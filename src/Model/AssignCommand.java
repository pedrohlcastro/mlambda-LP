package Model;

import java.util.ArrayList;
import java.util.List;

public class AssignCommand extends Command {
 
  private List<Variable> vars;
  private Value<?> value;
 
  public AssignCommand(Value<?> value, int line) {
    super(line);
    this.vars = new ArrayList<Variable>();
    this.value = value;
  }
 
  public void addVariable(Variable v) {
    this.vars.add(v);
  }
 
  @Override
  public void execute() {
        Value<?> value = (this.value instanceof Variable) ? ((Variable) this.value).value() : this.value;

        Value<?> newValue = null;
        if (value instanceof IntValue) {
          IntValue iv = (IntValue) value;
          newValue = new ConstIntValue(iv.value(), -1);
        } else if (value instanceof StringValue) {
          StringValue sv = (StringValue) value;
          newValue = new ConstStringValue(sv.value(), -1);
        } else if (value instanceof ArrayValue) {
          ArrayValue av = (ArrayValue) value;
          newValue = new ConstArrayValue(av.value());
        } else {
          System.err.println("[ASSIGN NOT SUPPORTED FOR THIS TYPE] LINE - " + super.getLine());
          System.exit(0);
        }

        for (Variable v : this.vars) {
          v.setValue(newValue);
        }
    }
}