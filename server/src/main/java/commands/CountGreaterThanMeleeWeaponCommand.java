package commands;

import data.MeleeWeapon;
import data.SpaceMarine;
import exceptions.ElementAmountException;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;

public class CountGreaterThanMeleeWeaponCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;

    public CountGreaterThanMeleeWeaponCommand(CollectionHandler collectionHandler) {
        super("count_greater_than_melee_weapon", "   print the number of elements, " +
                "the value of the meleeWeapon field is greater than the given one");
        this.collectionHandler = collectionHandler;
    }
    public boolean argCheck(String arg){
        try{
            if(arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            Console.printerror("Invalid number of arguments");
        }
        return false;
    }


    @Override
    public String execute(Request request) {
        if (argCheck(request.getArguments())) {
            MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(request.getArguments().toUpperCase());
            HashSet<SpaceMarine> marinesMelee = collectionHandler.enumerationMelee(meleeWeapon);
            int quantityMelee = (int) collectionHandler.getCollection().stream()
                    .filter(spaceMarine -> spaceMarine.getMeleeWeapon() == meleeWeapon)
                    .count();
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true);
            printWriter.println("Element amount with melee weapon is higher than: " + quantityMelee);
            return stringWriter.toString();
    }else {
            return "";
        }
    }
}