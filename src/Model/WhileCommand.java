package Model;

public class WhileCommand extends Command{
    private BoolValue expr;
    private Command cmd;
    
    public WhileCommand (BoolValue expr, Command cmd, int line){
        super(line);
        this.expr = expr;
        this.cmd = cmd;
    }

    @Override
    public void execute() {
    }
}
