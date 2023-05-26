package commands;

import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import requests.Request;
import utils.Console;

public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit", "exit the program");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg != "placeholderArg") throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        }
        return false;
    }
    @Override
    public void execute(Request request){
        if(argCheck(request.getArguments())){
            System.exit(0);
        }
    }//WIP
}