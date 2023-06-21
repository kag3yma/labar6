package commands;

import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;

public class InfoCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public InfoCommand(CollectionHandler collectionHandler) {
        super("info", "display information about the collection");
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
    public String execute(Request request) {
        if(argCheck(request.getArguments())){
                String output = "";
                LocalDateTime lastInitTime = collectionHandler.getInitDateTime();
                String lastInitTimeString = (lastInitTime == null) ? "initialization has not yet taken place in this session" :
                        lastInitTime.toString();


                output += ("\nCollection details:");
                output += ("\n Type: " + collectionHandler.collectionType());
                output += ("\n Amount of elements: " + collectionHandler.collectionSize());
                output += ("\n Date of last initialization: " + lastInitTimeString);
            return output;
    }
        return "";
    }
}