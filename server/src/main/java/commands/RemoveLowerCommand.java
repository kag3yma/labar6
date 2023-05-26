package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;
import utils.MarineAsker;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class RemoveLowerCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public RemoveLowerCommand(CollectionHandler collectionHandler, TCPServer server) {
        super("remove_lower", "remove from the collection all elements smaller than the given one");
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
        if(argCheck(request.getArguments())){
            try{
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
            if (collectionHandler.enumeration(request.getSpaceMarine().getHealth()) == 1) {
                output.println("Soldiers successfully removed!");
            } else if (collectionHandler.enumeration(request.getSpaceMarine().getHealth()) == 2) {
                output.println("The value of the soldier is less than all the soldiers in the collection!");
            } else output.println("Collection is empty!");
        }catch(IOException e){Console.printerror(e.getMessage());}
        }
    }
}

