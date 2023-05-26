package commands;


import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.io.IOException;
import java.io.PrintWriter;


public class AverageOfHealthCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public AverageOfHealthCommand(CollectionHandler collectionHandler, TCPServer server) {
        super("average_of_health", "display the average value of the health field for all elements of the collection");
        this.collectionHandler = collectionHandler;
        this.server = server;
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
    public void execute(Request request) {
        if (argCheck(request.getArguments())) {
            try{
            PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
            if (collectionHandler.averageHealth() > 0) {
                output.println(collectionHandler.averageHealth());
            } else output.println("Collection is empty!");
    }catch(IOException ioe){Console.printerror(ioe.getMessage());}}
}}