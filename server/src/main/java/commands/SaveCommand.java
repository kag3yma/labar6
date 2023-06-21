package commands;

import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;

public class SaveCommand extends AbstractCommand {
    private  DatabaseHandler databaseHandler;
    private CollectionHandler collectionHandler;

    public SaveCommand(CollectionHandler collectionHandler, DatabaseHandler databaseHandler) {
        super("save", "save collection to database");
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
    public String execute(Request request){
        if(argCheck(request.getArguments())){
                collectionHandler.getCollection().stream().forEach(spaceMarine -> databaseHandler.saveMarine(spaceMarine));
        }
        return "";
    }

}
