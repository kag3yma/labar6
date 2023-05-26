package commands;

import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import utils.CommandHelper;
import utils.Console;

public class HelpCommand extends AbstractCommand {

    public HelpCommand(){
        super("help", "print help for all available commands");
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

    @Override
    public void execute(String arg){
        if(argCheck(arg)){
            Console.println(".................");
            for (String name: CommandHelper.commandList().keySet()) {
                String value = CommandHelper.commandList().get(name);
                Console.println("\u001B[36m" + name + "\u001B[0m" + " - " + value + "\n..............");
            }
        }
    }
}