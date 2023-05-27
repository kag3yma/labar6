package commands;

import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
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
            try {
                PrintWriter output = new PrintWriter(server.getClientSocket().getOutputStream(), true);
                FileTime lastInitTime = collectionHandler.getInitDateTime();
                String lastInitTimeString = (lastInitTime == null) ? "initialization has not yet taken place in this session" :
                        lastInitTime.toString();

                LocalDateTime lastSaveTime = collectionHandler.getLastSaveTime();
                String lastSaveTimeString = (lastSaveTime == null) ? "this session has not yet been saved" :
                        lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

                output.println("Collection details:");
                output.println(" Type: " + collectionHandler.collectionType());
                output.println(" Amount of elements: " + collectionHandler.collectionSize());
                output.println(" Last save date: " + lastSaveTimeString);
                output.println(" Date of last initialization: " + lastInitTimeString);
            }catch (IOException e){ Console.printerror(e.getMessage());}
    }}
}