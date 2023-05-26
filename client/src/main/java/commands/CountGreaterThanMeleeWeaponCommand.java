package commands;

import data.MeleeWeapon;
import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionHandler;
import utils.Console;

import java.util.HashSet;

public class CountGreaterThanMeleeWeaponCommand extends AbstractCommand {

    public CountGreaterThanMeleeWeaponCommand()  {
        super("count_greater_than_melee_weapon", "print the number of elements, the value of the meleeWeapon field is greater than the given one");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Wrong number of arguments");
        }
        return false;
    }
}