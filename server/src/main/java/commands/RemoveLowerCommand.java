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

public class RemoveLowerCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private MarineAsker marineAsker;

    public RemoveLowerCommand(CollectionHandler collectionHandler, MarineAsker marineAsker) {
        super("remove_lower", "remove from the collection all elements smaller than the given one");
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
        if(argCheck(request.getArguments())){
        try {
            SpaceMarine marineToCompare = new SpaceMarine(
                    collectionHandler.generateNextId(),
                    marineAsker.askName(),
                    LocalDateTime.now(),
                    marineAsker.askCoordinates(),
                    marineAsker.askHealth(),
                    marineAsker.askHeight(),
                    marineAsker.askWeaponType(),
                    marineAsker.askMeleeWeapon(),
                    marineAsker.askChapter()
            );
            if (collectionHandler.enumeration(marineToCompare.getHealth()) == 1) {
                Console.println("Soldiers successfully removed!");
            } else if (collectionHandler.enumeration(marineToCompare.getHealth()) == 2) {
                Console.printerror("The value of the soldier is less than all the soldiers in the collection!");
            } else Console.printerror("Collection is empty!");
        }  catch (IncorrectInputInScriptException exception) {}
    }}
}

