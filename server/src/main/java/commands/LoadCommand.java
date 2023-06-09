package commands;

import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.Serializator;

public class LoadCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public LoadCommand(CollectionHandler collectionHandler) {
        super("load", "");
        this.collectionHandler = collectionHandler;
    }

    @Override
    public boolean argCheck(String arg) {
        try {
            if (!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
        }
        return false;
    }
    @Override
    public String execute(Request request){
        if (argCheck(request.getArguments())){
            Serializator s = new Serializator();
            String json = s.serializetoJson(collectionHandler);
            return json;
        }else {
            return "";
        }
    }
}
