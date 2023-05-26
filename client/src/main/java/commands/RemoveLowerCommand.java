package commands;

import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionHandler;
import utils.Console;
import utils.MarineAsker;

import java.time.LocalDateTime;

public class RemoveLowerCommand extends AbstractCommand {


    public RemoveLowerCommand() {
        super("remove_greater", " remove from the collection all elements smaller than the given one");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            Integer.parseInt(arg);

            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Некорректное кол-во аргументов");
        } catch (NumberFormatException ex) {
            Console.printerror("Введите число");
        }
        return false;
    }
}

