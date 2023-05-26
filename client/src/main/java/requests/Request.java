package requests;
import java.io.Serializable;
import data.SpaceMarine;

public class Request implements Serializable {
    private String commandname;
    private String argument;
    private SpaceMarine spaceMarine;
    static final long serialVersionUID = 1L;

    public Request(String commandName, String argument, SpaceMarine spaceMarine){
        this.commandname = commandName;
        this.argument = argument;
        this.spaceMarine = spaceMarine;
    }

    public String getCommandName(){
        return commandname;
    }
    public String getArgument(){
        return argument;
    }

    public SpaceMarine getMarineAsker() {
        return spaceMarine;
    }
}