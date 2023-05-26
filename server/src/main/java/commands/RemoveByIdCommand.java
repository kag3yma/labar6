package commands;

import data.SpaceMarine;
import exceptions.CollectionIsEmptyException;
import exceptions.ElementAmountException;
import exceptions.MarineNotFoundException;
import exceptions.WrongAmountOfElementsException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

public class RemoveByIdCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public RemoveByIdCommand(CollectionHandler collectionHandler) {
        super("remove_by_id <ID>", "remove item from collection by ID");
        this.collectionHandler = collectionHandler;
    }
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            Integer.parseInt(arg);

            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        } catch (NumberFormatException ex) {
            Console.printerror("Enter number");
        }
        return false;
    }


    @Override
    public void execute(Request request) {
        if(argCheck(request.getArguments())) {
        try {
            if (collectionHandler.collectionSize() == 0) throw new CollectionIsEmptyException();
            Long id = Long.parseLong(request.getArguments());
            SpaceMarine marineToRemove = collectionHandler.getById(id);
            if (marineToRemove == null) throw new MarineNotFoundException();
            collectionHandler.removeFromCollection(marineToRemove);
            Console.println("Soldier successfully removed!");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Collection is empty!");
        } catch (NumberFormatException exception) {
            Console.printerror("ID must be represented by a number!");
        } catch (MarineNotFoundException exception) {
            Console.printerror("There is no soldier with this ID in the collection!");
        }
    }}
}
