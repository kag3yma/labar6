package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionHandler;
import utils.Console;

import java.util.HashSet;


public class FilterStartsWithNameCommand extends AbstractCommand {

    public FilterStartsWithNameCommand() {
        super("filter_starts_with_name", "display elements whose name field value starts with the given substring");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Wrong number of arguments");
        }
        return false;
    }
}
