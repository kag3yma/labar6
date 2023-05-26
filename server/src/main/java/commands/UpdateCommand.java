package commands;

import data.*;
import exceptions.*;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.MarineAsker;

import java.time.LocalDateTime;

public class UpdateCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private MarineAsker marineAsker;

    public UpdateCommand(CollectionHandler collectionHandler, MarineAsker marineAsker) {
        super("update", "update collection element value by ID");
        this.collectionHandler = collectionHandler;
        this.marineAsker = marineAsker;
    }
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
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
    public void execute(Request request){
        if(argCheck(request.getArguments())){
            for(SpaceMarine spaceMarine :  collectionHandler.getCollection()){
                if(spaceMarine.getId()==(Long.valueOf(request.getArguments()))){
                    collectionHandler.removeFromCollection(spaceMarine);
                    SpaceMarine newSpaceMarine = request.getSpaceMarine();
                    newSpaceMarine.setId(Long.valueOf(request.getArguments()));
                    collectionHandler.addToCollection(newSpaceMarine);
                }
            }
        }
    }
}