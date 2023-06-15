package requests;
import java.io.Serializable;
import data.SpaceMarine;
import data.User;

public class Request implements Serializable {
    private String commandname;
    private String argument;
    private SpaceMarine spaceMarine;
    private User user;
    static final long serialVersionUID = 1L;

    public Request(String commandName, String argument, SpaceMarine spaceMarine, User user){
        this.commandname = commandName;
        this.argument = argument;
        this.spaceMarine = spaceMarine;
        this.user = user;
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
    public User getUser(){return user;}
}