package commands;

import data.SpaceMarine;
import exceptions.CollectionIsEmptyException;
import exceptions.ElementAmountException;
import exceptions.MarineNotFoundException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.io.IOException;
import java.io.PrintWriter;

public class RemoveByIdCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public RemoveByIdCommand(CollectionHandler collectionHandler, TCPServer server) {
        super("remove_by_id", "remove item from collection by ID");
        this.collectionHandler = collectionHandler;
        this.server = server;
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
    public String execute(Request request) {
        if(argCheck(request.getArguments())) {
            try{
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
        try {
            if (collectionHandler.collectionSize() == 0) throw new CollectionIsEmptyException();
            Long id = Long.parseLong(request.getArguments());
            SpaceMarine marineToRemove = collectionHandler.getById(id);
            if (marineToRemove == null) throw new MarineNotFoundException();
            collectionHandler.removeFromCollection(marineToRemove);
            output.println("Soldier successfully removed!");
        } catch (CollectionIsEmptyException exception) {
            output.println("Collection is empty!");
        } catch (NumberFormatException exception) {
            output.println("ID must be represented by a number!");
        } catch (MarineNotFoundException exception) {
            output.println("There is no soldier with this ID in the collection!");
        }
    }catch (IOException e){ Console.printerror(e.getMessage());}}
        return null;
    }}
