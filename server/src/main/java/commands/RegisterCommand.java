package commands;

import exceptions.ElementAmountException;
import requests.Request;
import utils.Console;
import utils.DatabaseHandler;
import utils.Hasher;

import java.sql.SQLException;

public class RegisterCommand extends AbstractCommand{
    private DatabaseHandler databaseHandler;

    public RegisterCommand(DatabaseHandler databaseHandler){
        super("register", "");
        this.databaseHandler = databaseHandler;
    }

    @Override
    public boolean argCheck(String arg){
        try {
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        }catch (ElementAmountException e){
            Console.println("Wrong amount of elements");
        }return false;
    }

    @Override
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            Hasher hasher = new Hasher("SHA-224");
            if(!databaseHandler.checkIfUserExists(request.getUser().getLogin(), hasher.encode(request.getUser().getPassword()))){
                try {
                    databaseHandler.register(request.getUser().getLogin(), request.getUser().getPassword());
                }catch (SQLException sqle){
                    return "This user already exists";
                }
            }else {
                return "This user already exists";
            }
        }return "User successfully registered";
    }
}
