package commands;

import data.*;
import exceptions.*;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;
import utils.MarineAsker;

import java.util.NoSuchElementException;

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
            Long.parseLong(arg);

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
        if(argCheck(request.getArguments())) {
            try {

                for (SpaceMarine spaceMarine : collectionHandler.getCollection()) {
                    if (spaceMarine.getId() == (Long.parseLong(request.getArguments()))) {
                        SpaceMarine nMarine = request.getSpaceMarine();
                        nMarine.setId(Long.parseLong(request.getArguments()));
                        if (spaceMarine.getCreator().equals(request.getUser().getLogin())) {
                            databaseHandler.deleteSpaceMarine(spaceMarine.getId());
                            collectionHandler.removeFromCollection(spaceMarine);

                            collectionHandler.addToCollection(nMarine);
                            Request nRequest = new Request(request.getCommandName(), "placeholderArg", nMarine, request.getUser());
                            new SaveCommand(collectionHandler, databaseHandler).execute(nRequest);
                        } else {
                            return "Can't edit items created by other users";
                        }
                    }
                }
            } catch (NoSuchElementException e) {
                return e.getMessage();
            }
        }
        return "";
    }
}