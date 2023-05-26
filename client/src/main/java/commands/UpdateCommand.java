package commands;

import data.*;
import exceptions.*;
import utils.CollectionHandler;
import utils.Console;
import utils.MarineAsker;

import java.time.LocalDateTime;

public class UpdateCommand extends AbstractCommand {

    public UpdateCommand(){
        super("update", "update collection element value by ID");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            Integer.parseInt(arg);

            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Wrong number of arguments");
        } catch (NumberFormatException ex) {
            Console.printerror("Enter number");
        }
        return false;
    }
}