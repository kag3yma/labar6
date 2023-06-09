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
                FileTime lastInitTime = collectionHandler.getInitDateTime();
                String lastInitTimeString = (lastInitTime == null) ? "initialization has not yet taken place in this session" :
                        lastInitTime.toString();

                LocalDateTime lastSaveTime = collectionHandler.getLastSaveTime();
                String lastSaveTimeString = (lastSaveTime == null) ? "this session has not yet been saved" :
                        lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

                output += ("Collection details:");
                output += (" Type: " + collectionHandler.collectionType());
                output += (" Amount of elements: " + collectionHandler.collectionSize());
                output += (" Last save date: " + lastSaveTimeString);
                output += (" Date of last initialization: " + lastInitTimeString);
    }
        return null;
    }
}