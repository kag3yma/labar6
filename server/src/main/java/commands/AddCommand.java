package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.MarineAsker;

public class AddCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private MarineAsker marineAsker;

    public AddCommand(CollectionHandler collectionHandler, MarineAsker marineAsker) {
        super("add", "add a new element to the collection");
        this.collectionHandler = collectionHandler;
        this.marineAsker = marineAsker;
    }
    @Override
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
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            SpaceMarine spaceMarine = request.getSpaceMarine();
            spaceMarine.setId(collectionHandler.generateNextId());
            collectionHandler.addToCollection(spaceMarine);
    }
        return null;
    }
}
