package commands;

import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;

import java.sql.SQLException;

public class ClearCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private DatabaseHandler databaseHandler;

    public ClearCommand(CollectionHandler collectionHandler, DatabaseHandler databaseHandler) {
        super("clear", "clear the collection");
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
    public String execute(Request request) throws SQLException {
        if (argCheck(request.getArguments())) {
            collectionHandler.clearCollection();
            databaseHandler.clearCollection();
    }
        return "Collection cleared!";
    }
}