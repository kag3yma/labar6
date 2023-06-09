package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.DatabaseHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.stream.Collectors;

public class RemoveLowerCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private DatabaseHandler databaseHandler;

    public RemoveLowerCommand(CollectionHandler collectionHandler, DatabaseHandler databaseHandler) {
        super("remove_lower", "remove from the collection all elements smaller than the given one");
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
    public String execute(Request request) {
        if(argCheck(request.getArguments())){
            boolean checkList = collectionHandler.getCollection().stream()
                    .filter(spaceMarine -> !spaceMarine.getCreator().equals(request.getUser().getLogin()))
                    .filter(spaceMarine -> spaceMarine.getId() <= Long.parseLong(request.getArguments()))
                    .collect(Collectors.toCollection(HashSet::new)).size() > 0;
                    if (checkList){
                        return "You can't edit elements which are not yours";
                    }
                    HashSet<SpaceMarine> filteredList = collectionHandler.getCollection()
                            .stream()
                            .filter(spaceMarine -> spaceMarine.getId() <= Long.parseLong(request.getArguments()))
                            .collect(Collectors.toCollection(HashSet::new));
                    for (SpaceMarine spaceMarine: collectionHandler.getCollection()){
                        if (!filteredList.contains(spaceMarine)){
                            databaseHandler.deleteSpaceMarine(spaceMarine.getId());
                        }
                    }
                    collectionHandler.clearCollection();
                    collectionHandler.setCollection(filteredList);
        }
        return "";
    }
}

