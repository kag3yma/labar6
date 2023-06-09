package commands;

import exceptions.ElementAmountException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ShowCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public ShowCommand(CollectionHandler collectionHandler) {
        super("show", "display all elements of the collection");
        this.collectionHandler = collectionHandler;
    }
    public boolean argCheck(String arg){
        try{
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {}
        return false;
    }

    @Override
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true);
            collectionHandler.printSpaceMarinesList(printWriter);
            return stringWriter.toString();
        }else {
            return "";
        }
    }
}