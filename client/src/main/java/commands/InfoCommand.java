package commands;

import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionHandler;
import utils.Console;

import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;

public class InfoCommand extends AbstractCommand {

    public InfoCommand() {
        super("info", "display information about the collection");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Wrong number of arguments");
        }
        return false;
    }
}