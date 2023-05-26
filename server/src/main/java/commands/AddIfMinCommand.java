package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.MarineAsker;

import java.time.LocalDateTime;

public class AddIfMinCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private MarineAsker marineAsker;

    public AddIfMinCommand(CollectionHandler collectionHandler, MarineAsker marineAsker) {
        super("add_if_min {element}", "add a new element if its value is less than that of the smallest");
        this.collectionHandler = collectionHandler;
        this.marineAsker = marineAsker;
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
    public void execute(Request request) {
        if (argCheck(request.getArguments())) {
            SpaceMarine spaceMarine = request.getSpaceMarine();
            spaceMarine.setId(collectionHandler.generateNextId());
            if (collectionHandler.collectionSize() == 0 ||
                    spaceMarine.healthCompareTo(collectionHandler.getById(collectionHandler.getMin())) < 0) {
                collectionHandler.addToCollection(spaceMarine);
                Console.println("Soldier successfully added!");
                } else
                    Console.printerror("The value of the soldier is greater than the value of the smallest of the soldiers!");
            }
        }
    }