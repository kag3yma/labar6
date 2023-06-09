package requests;
import java.io.Serializable;
import data.SpaceMarine;
import data.User;
import utils.MarineAsker;

public class Request implements Serializable {
    private String commandname;
    private String argument;
    private SpaceMarine spaceMarine;
    private User user;
    static final long serialVersionUID = 1L;

    public Request(String commandname, String argument, SpaceMarine spaceMarine, User user){
        this.commandname = commandname;
        this.argument = argument;
        this.spaceMarine = spaceMarine;
        this.user = user;
    }

    public String getCommandName(){
        return commandname;
    }
    public String getArguments(){
        return argument;
    }

    public SpaceMarine getSpaceMarine() {
        return spaceMarine;
    }
    public void writeArg(){
        System.out.println(commandname);
        System.out.println(argument);
    }
    public User getUser(){return user;}
}