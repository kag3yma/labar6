package commands;

import exceptions.ElementAmountException;
import requests.Request;
import utils.Console;
import utils.DatabaseHandler;
import utils.Hasher;

public class LoginCommand extends AbstractCommand{
    private DatabaseHandler databaseHandler;
    public LoginCommand(DatabaseHandler databaseHandler){
        super("login", "");
        this.databaseHandler = databaseHandler;
    }
    @Override
    public boolean argCheck(String arg){
        try {
            if (!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        }catch (ElementAmountException e){
            Console.println("Wrong amount of elements");
        }return false;
    }
    @Override
    public String execute(Request request){
        if (argCheck(request.getArguments())){
            Hasher hasher = new Hasher("SHA-224");
            if (!databaseHandler.checkIfUserExists(request.getUser().getLogin(), hasher.encode(request.getUser().getPassword()))){
                return "Wrong login or password";
            }else {
                return "Now you are able to use all commands. Use help to see all available commands";
            }
        }return "";
    }
}
