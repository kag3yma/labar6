package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;

import java.util.NoSuchElementException;
import java.util.Optional;

public class RemoveByIdCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private DatabaseHandler databaseHandler;

    public RemoveByIdCommand(CollectionHandler collectionHandler, DatabaseHandler databaseHandler) {
        super("remove_by_id", "remove item from collection by ID");
        this.collectionHandler = collectionHandler;
        this.databaseHandler = databaseHandler;
    }

    public boolean argCheck(String arg) {
        try {
            if (arg.equals("placeholderArg")) throw new ElementAmountException();
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
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            try {

                Optional<SpaceMarine> bufferedSpaceMarine = collectionHandler.getCollection().stream().filter(spaceMarine -> spaceMarine.getId() == Long.parseLong(
                        request.getArguments())).findFirst();
                if (bufferedSpaceMarine.get().getCreator().equals(request.getUser().getLogin())) {
                    bufferedSpaceMarine.ifPresent(collectionHandler::removeFromCollection);
                    databaseHandler.deleteSpaceMarine(bufferedSpaceMarine.get().getId());
                    return "Successfully removed!";

                } else {
                    return "Can't edit items created by other users";
                }
            } catch (NoSuchElementException nse) {
                return nse.getMessage();
            }
        }
        return "";
    }
}
