package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.MarineAsker;

public class AddIfMaxCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private MarineAsker marineAsker;

    public AddIfMaxCommand(CollectionHandler collectionHandler, MarineAsker marineAsker) {
        super("add_if_max", "add a new element to the collection, " +
                "if its value is greater than the value of the largest element of this collection");
        this.collectionHandler = collectionHandler;
        this.marineAsker = marineAsker;
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
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            SpaceMarine spaceMarine = request.getSpaceMarine();
            spaceMarine.setId(collectionHandler.generateNextId());
            if (collectionHandler.collectionSize() == 0 ||
                    spaceMarine.healthCompareTo(collectionHandler.getById(collectionHandler.getMax())) > 0) {
                collectionHandler.addToCollection(spaceMarine);
                Console.println("Soldier successfully added!");
            } else
                Console.printerror("The value of the soldier is smaller than the value of the greatest of the soldiers!");
        }
        return null;
    }
}