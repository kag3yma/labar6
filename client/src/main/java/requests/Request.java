package requests;
import java.io.Serializable;
import data.SpaceMarine;
import utils.MarineAsker;

public class Request implements Serializable {
    private String commandname;
    private String argument;
    private SpaceMarine spaceMarine;
    static final long serialVersionUID = 1L;

    public Request(String commandname, String argument, SpaceMarine spaceMarine){
        this.commandname = commandname;
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