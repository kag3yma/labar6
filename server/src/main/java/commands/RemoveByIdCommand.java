package commands;

import data.SpaceMarine;
import exceptions.CollectionIsEmptyException;
import exceptions.ElementAmountException;
import exceptions.MarineNotFoundException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;

import java.io.IOException;
import java.io.PrintWriter;
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
            Optional<SpaceMarine> bufferedSpaceMarine = collectionHandler.getCollection().stream().filter(spaceMarine -> spaceMarine.getId() == Long.parseLong(
                    request.getArguments())).findFirst();
            if (bufferedSpaceMarine.get().getCreator().equals(request.getUser().getLogin())) {
                bufferedSpaceMarine.ifPresent(collectionHandler::removeFromCollection);
                databaseHandler.deleteSpaceMarine(bufferedSpaceMarine.get().getId());
            }
        }
        return "";
    }
}
