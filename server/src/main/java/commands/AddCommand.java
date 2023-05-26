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

public class AddCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private MarineAsker marineAsker;

    public AddCommand(CollectionHandler collectionHandler, MarineAsker marineAsker) {
        super("add {element}", "add a new element to the collection");
        this.collectionHandler = collectionHandler;
        this.marineAsker = marineAsker;
    }
    @Override
    public boolean argCheck(String arg){
        try{
            if(arg !="placeholderArg") throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
           Console.printerror("Invalid number of arguments");
        }
        return false;
    }

    @Override
    public void execute(Request request) {
        if (argCheck(request.getArguments())) {
        try {

            boolean solution = marineAsker.askWantChapter();
            if (solution){
                collectionHandler.addToCollection(new SpaceMarine(
                        collectionHandler.generateNextId(),
                        marineAsker.askName(),
                        LocalDateTime.now(),
                        marineAsker.askCoordinates(),
                        marineAsker.askHealth(),
                        marineAsker.askHeight(),
                        marineAsker.askWeaponType(),
                        marineAsker.askMeleeWeapon(),
                        marineAsker.askChapter()
                ));
                Console.println("Soldier successfully added!");
                }
            if (!solution){
                collectionHandler.addToCollection(new SpaceMarine(
                        collectionHandler.generateNextId(),
                        marineAsker.askName(),
                        LocalDateTime.now(),
                        marineAsker.askCoordinates(),
                        marineAsker.askHealth(),
                        marineAsker.askHeight(),
                        marineAsker.askWeaponType(),
                        marineAsker.askMeleeWeapon()
                ));
                Console.println("Soldier successfully added!");
                }
        }  catch (IncorrectInputInScriptException exception) {}
    }
    }
}
