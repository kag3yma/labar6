package commands;

import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

public class SaveCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public SaveCommand(CollectionHandler collectionHandler) {
        super("save", "save collection to file");
        this.collectionHandler = collectionHandler;
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
                collectionHandler.saveCollection();
        }
        return null;
    }

}
