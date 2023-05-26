package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.util.HashSet;


public class FilterStartsWithNameCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public FilterStartsWithNameCommand(CollectionHandler collectionHandler, TCPServer server) {
        super("filter_starts_with_name", "display elements whose name field value starts with the given substring");
        this.collectionHandler = collectionHandler;
        this.server = server;
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
    public void execute(Request request) {
        if (argCheck(request.getArguments())) {
            HashSet<SpaceMarine> marinesNames = collectionHandler.namestart(request.getArguments());
            if (!marinesNames.isEmpty()) {
                for(SpaceMarine marine: marinesNames) Console.println(marine.getName());
            } else Console.println("No matches found!");
        }
    }
}
