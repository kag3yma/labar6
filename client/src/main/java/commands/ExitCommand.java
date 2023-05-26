package commands;

import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import utils.Console;

public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", "terminate program");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Wrong number of arguments");
        }
        return false;
    }
    @Override
    public void execute(String arg){
        if(argCheck(arg)){
            System.exit(0);
        }
}
}
