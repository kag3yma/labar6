package commands;


import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;


public class AverageOfHealthCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public AverageOfHealthCommand(CollectionHandler collectionHandler) {
        super("average_of_health", "display the average value of the health field for all elements of the collection");
        this.collectionHandler = collectionHandler;
    }
    public boolean argCheck(String arg){
        try{
            if(arg !="placeholderArg") throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        }
        return false;
    }

    @Override
    public void execute(Request request) {
        if (argCheck(request.getArguments())) {
            if (collectionHandler.averageHealth() > 0) {
                System.out.println(collectionHandler.averageHealth());
            } else System.out.println("Collection is empty!");
    }}
}