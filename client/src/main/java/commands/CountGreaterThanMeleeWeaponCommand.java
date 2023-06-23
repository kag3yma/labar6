package commands;

import data.MeleeWeapon;
import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import utils.CollectionHandler;
import utils.Console;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CountGreaterThanMeleeWeaponCommand extends AbstractCommand {

    public CountGreaterThanMeleeWeaponCommand()  {
        super("count_greater_than_melee_weapon", "print the number of elements, the value of the meleeWeapon field is greater than the given one");
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            MeleeWeapon[] weapons = MeleeWeapon.values();
            List<String> weaponNames = new ArrayList<>();

            for (MeleeWeapon color : weapons) {
                weaponNames.add(String.valueOf(color));
            }
            if (weaponNames.contains(arg)) {return true;}
            Console.printerror("Incorrect type of MeleeWeapon");
            return false;
        } catch (ElementAmountException e) {
            Console.printerror("Wrong number of arguments");
        }
        return false;
    }
}