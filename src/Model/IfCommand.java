package Model;

public class IfCommand extends Command{
    private BoolValue expr;
    private Command then;
    private Command elses;
    
    public IfCommand(BoolValue expr, Command then, int line) {
        super(line);
        this.expr = expr;
        this.then = then;
    }
    
    public IfCommand(BoolValue expr, Command then, Command elses, int line) {
        super(line);
        this.expr = expr;
        this.then = then;
        this.elses = elses;
    }
    
    @Override
    public void execute(){}
    
}
