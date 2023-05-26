package commands;

import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionHandler;
import utils.Console;

public class ClearCommand extends AbstractCommand{

    public ClearCommand() {
        super("clear", "clear the collection");
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
}