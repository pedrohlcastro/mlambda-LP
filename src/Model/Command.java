package Model;

public abstract class Command {
    private int line;
    
    public Command (int line){
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public abstract void execute();
    
}
