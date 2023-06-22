package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;


public class FilterStartsWithNameCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public FilterStartsWithNameCommand(CollectionHandler collectionHandler) {
        super("filter_starts_with_name", "display elements whose name field value starts with the given substring");
        this.collectionHandler = collectionHandler;
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
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            try (StringWriter stringWriter = new StringWriter()){
                PrintWriter printWriter = new PrintWriter(stringWriter, true);
                collectionHandler.getCollection().stream()
                        .filter(spaceMarine -> spaceMarine.getName().contains(request.getArguments()))
                        .forEach(spaceMarine -> collectionHandler.printSpaceMarine(spaceMarine, printWriter));
                return stringWriter.toString();
            }catch (IOException ioe){
                return "Error: " + ioe.getMessage();
            }
        }else {
            return "";
        }
    }
}
