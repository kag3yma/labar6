package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionHandler;
import utils.Console;
import utils.MarineAsker;

import java.time.LocalDateTime;

public class AddCommand extends AbstractCommand {

    public AddCommand(){
        super("add", "add new element to the collection");
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
