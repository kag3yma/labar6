package commands;

import data.SpaceMarine;
import exceptions.CollectionIsEmptyException;
import exceptions.ElementAmountException;
import exceptions.MarineNotFoundException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionHandler;
import utils.Console;

public class RemoveByIdCommand extends AbstractCommand {

    public RemoveByIdCommand() {
        super("remove_by_id", "remove item from collection by ID");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            Integer.parseInt(arg);

            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Wrong number of arguments");
        } catch (NumberFormatException ex) {
            Console.printerror("Enter number");
        }
        return false;
    }
}
