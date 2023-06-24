package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RemoveLowerCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private DatabaseHandler databaseHandler;

    public RemoveLowerCommand(CollectionHandler collectionHandler, DatabaseHandler databaseHandler) {
        super("remove_lower", "remove from the collection all elements smaller than the given one");
        this.collectionHandler = collectionHandler;
        this.databaseHandler = databaseHandler;
    }
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();

            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        }
        return false;
    }

    @Override
    public String execute(Request request) throws SQLException {
        if (argCheck(request.getArguments())) {

            collectionHandler.getCollection()
                    .removeIf(spaceMarine -> spaceMarine.getCreator().equals(request.getUser().getLogin()) && spaceMarine.getId() <= Long.parseLong(request.getArguments()));

            databaseHandler.deleteSpaceMarine(request);

                }
        return "";
    }
}


