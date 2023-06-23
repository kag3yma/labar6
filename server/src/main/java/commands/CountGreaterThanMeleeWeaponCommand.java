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
import java.util.NoSuchElementException;
import java.util.Objects;

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
            try {

                MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(request.getArguments().toUpperCase());
                HashSet marinesMelee = collectionHandler.enumerationMelee(meleeWeapon);
                int quantityMelee = 0;
                for (Object element : marinesMelee) {
                    quantityMelee = quantityMelee + 1;
                }
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter, true);
                printWriter.println("Element amount with melee weapon is higher than: " + quantityMelee);
                return stringWriter.toString();
            } catch (NoSuchElementException nse) {
                return "Error: no such element";
            }
        }
        return "";
    }
}