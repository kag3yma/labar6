package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;
import utils.MarineAsker;

public class AddCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private DatabaseHandler databaseHandler;

    public AddCommand(CollectionHandler collectionHandler, DatabaseHandler databaseHandler) {
        super("add", "add a new element to the collection");
        this.collectionHandler = collectionHandler;
        this.databaseHandler = databaseHandler;
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
            spaceMarine.setId(databaseHandler.saveMarine(spaceMarine));
            collectionHandler.addToCollection(spaceMarine);
    }
        return "";
    }
}
