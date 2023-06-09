package commands;

import data.*;
import exceptions.*;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;
import utils.MarineAsker;

public class UpdateCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private DatabaseHandler databaseHandler;

    public UpdateCommand(CollectionHandler collectionHandler, DatabaseHandler databaseHandler) {
        super("update", "update collection element value by ID");
        this.collectionHandler = collectionHandler;
        this.databaseHandler = databaseHandler;
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
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            for(SpaceMarine spaceMarine :  collectionHandler.getCollection()){
                if(spaceMarine.getId()==(Long.valueOf(request.getArguments()))){
                    collectionHandler.removeFromCollection(spaceMarine);
                    SpaceMarine newSpaceMarine = request.getSpaceMarine();
                    newSpaceMarine.setId(Long.valueOf(request.getArguments()));
                    if(spaceMarine.getCreator().equals(request.getUser().getLogin())){
                        collectionHandler.removeFromCollection(spaceMarine);
                        collectionHandler.addToCollection(newSpaceMarine);
                    }else {
                        return "Yo can't edit elements which are not yours";
                    }
                }
            }
            new SaveCommand(collectionHandler, databaseHandler).execute(request);
        }
        return "";
    }
}