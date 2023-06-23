package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.function.Predicate;


public class FilterStartsWithNameCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    public FilterStartsWithNameCommand(CollectionHandler collectionHandler) {
        super("filter_starts_with_name", "display elements whose name field value starts with the given substring");
        this.collectionHandler = collectionHandler;
    }

    public boolean argCheck(String arg) {
        try {
            if (arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        }
        return false;
    }

    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            try (StringWriter sw = new StringWriter()) {
                PrintWriter output = new PrintWriter(sw, true);
                collectionHandler.getCollection().stream()
                        .filter(spaceMarine -> spaceMarine.getName().startsWith(request.getArguments()))
                        .forEach(spaceMarine -> collectionHandler.printSpaceMarine(spaceMarine, output));
                return sw.toString();
            } catch (IOException | NoSuchElementException ioe) {
                return ioe.getMessage();
            }
            }else{
                return "";
            }
        }
    }
