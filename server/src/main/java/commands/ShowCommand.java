package commands;

import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.io.IOException;
import java.io.PrintWriter;

public class ShowCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public ShowCommand(CollectionHandler collectionHandler, TCPServer server) {
        super("show", "display all elements of the collection");
        this.collectionHandler = collectionHandler;
        this.server = server;
    }
    public boolean argCheck(String arg){
        try{
            System.out.println(arg);
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            try{
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
                output.println("Invalid number of arguments");
            } catch (IOException ioe){
                Console.println(ioe);
            }
        }
        return false;
    }

    @Override
    public void execute(Request request) {
        if (argCheck(request.getArguments())){
            try{
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
                output.println(output);
                output.println(collectionHandler);
            } catch (IOException ioe){
                Console.println(ioe.getMessage());
            }

        }
    }
}