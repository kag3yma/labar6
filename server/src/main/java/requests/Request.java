package requests;
import java.io.Serializable;
import data.SpaceMarine;

public class Request implements Serializable {
    private String commandname;
    private String arguments;
    private SpaceMarine spaceMarine;
    public Request(String commandname, String argument, SpaceMarine spaceMarine){
        this.commandname = commandname;
        this.arguments = arguments;
        this.spaceMarine = spaceMarine;
    }

    public String getCommandName(){
        return commandname;
    }
    public String getArguments(){
        return arguments;
    }

    public SpaceMarine getSpaceMarine() {
        return spaceMarine;
    }
}