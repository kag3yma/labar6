package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;


public class FilterStartsWithNameCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public FilterStartsWithNameCommand(CollectionHandler collectionHandler, TCPServer server) {
        super("filter_starts_with_name", "display elements whose name field value starts with the given substring");
        this.collectionHandler = collectionHandler;
        this.server = server;
    }
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        }
        return false;
    }

    @Override
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            try{
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
            HashSet<SpaceMarine> marinesNames = collectionHandler.namestart(request.getArguments());
            if (!marinesNames.isEmpty()) {
                for(SpaceMarine marine: marinesNames) output.println(marine.getName());
            } else output.println("No matches found!");
        }catch(IOException ioe){Console.printerror(ioe.getMessage());}}
        return null;
    }
}
