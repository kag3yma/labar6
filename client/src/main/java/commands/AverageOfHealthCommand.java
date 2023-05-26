package commands;


import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionHandler;
import utils.Console;


public class AverageOfHealthCommand extends AbstractCommand {

    public AverageOfHealthCommand() {
        super("average_of_health", "display the average value of the health field for all elements of the collection");
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