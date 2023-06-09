package commands;


import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class AverageOfHealthCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private DatabaseHandler databaseHandler;

    public AverageOfHealthCommand(CollectionHandler collectionHandler, DatabaseHandler databaseHandler) {
        super("average_of_health", "display the average value of the health field for all elements of the collection");
        this.collectionHandler = collectionHandler;
        this.databaseHandler = databaseHandler;
    }

    public boolean argCheck(String arg) {
        try {
            if (!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        }
        return false;
    }

    @Override
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter output = new PrintWriter(stringWriter, true);
            if (collectionHandler.averageHealth() > 0) {
                output.println(collectionHandler.averageHealth());
            } else output.println("Collection is empty!");
        }return "";
    }
}