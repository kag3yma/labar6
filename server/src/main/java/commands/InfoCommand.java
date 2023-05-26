package commands;

import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;

public class InfoCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public InfoCommand(CollectionHandler collectionHandler, TCPServer server) {
        super("info", "display information about the collection");
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
            FileTime lastInitTime = collectionHandler.getInitDateTime();
            String lastInitTimeString = (lastInitTime == null) ? "initialization has not yet taken place in this session" :
                    lastInitTime.toString();

            LocalDateTime lastSaveTime = collectionHandler.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "this session has not yet been saved" :
                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

            System.out.println("Collection details:");
            System.out.println(" Type: " + collectionHandler.collectionType());
            System.out.println(" Amount of elements: " + collectionHandler.collectionSize());
            System.out.println(" Last save date: " + lastSaveTimeString);
            System.out.println(" Date of last initialization: " + lastInitTimeString);
    }}
}