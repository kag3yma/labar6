package commands;

import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

public class ClearCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public ClearCommand(CollectionHandler collectionHandler) {
        super("clear", "clear the collection");
        this.collectionHandler = collectionHandler;
    }
    public boolean argCheck(String arg){
        try{
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        }
        return false;
    }

    @Override
    public void execute(Request request) {
        if (argCheck(request.getArguments())) {
            collectionHandler.clearCollection();
            Console.println("Collection cleared!");
    }}
}