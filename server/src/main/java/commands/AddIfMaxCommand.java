package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;

public class AddIfMaxCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private DatabaseHandler databaseHandler;


    public AddIfMaxCommand(CollectionHandler collectionHandler, DatabaseHandler databaseHandler) {
        super("add_if_max", "add a new element to the collection, " +
                "if its value is greater than the value of the largest element of this collection");
        this.collectionHandler = collectionHandler;
        this.databaseHandler = databaseHandler;
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
            spaceMarine.setId(databaseHandler.saveMarine(spaceMarine));
            collectionHandler.addToCollection(spaceMarine);
            if (collectionHandler.collectionSize() != 0 &&
                    spaceMarine.healthCompareTo(collectionHandler.getById(collectionHandler.getMax())) < 0) {
                databaseHandler.deleteSpaceMarine(spaceMarine.getId());
                collectionHandler.removeFromCollection(spaceMarine);
                Console.printerror("The value of the soldier is smaller than the value of the greatest of the soldiers!");
            } else
                Console.println("Soldier successfully added!");
        }
        return "Soldier successfully added!";
    }
}