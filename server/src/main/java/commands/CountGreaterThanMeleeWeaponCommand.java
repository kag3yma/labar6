package commands;

import data.MeleeWeapon;
import data.SpaceMarine;
import exceptions.ElementAmountException;
import exceptions.WrongAmountOfElementsException;
import network.TCPServer;
import requests.Request;
import utils.CollectionHandler;
import utils.Console;

import java.util.HashSet;

public class CountGreaterThanMeleeWeaponCommand extends AbstractCommand {
    private CollectionHandler collectionHandler;
    private TCPServer server;

    public CountGreaterThanMeleeWeaponCommand(CollectionHandler collectionHandler, TCPServer server) {
        super("count_greater_than_melee_weapon meleeWeapon", "   print the number of elements, " +
                "the value of the meleeWeapon field is greater than the given one");
        this.collectionHandler = collectionHandler;
        this.server = server;
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
    public void execute(Request request) {
        if (argCheck(request.getArguments())) {
        try {
            MeleeWeapon MeleeWep = MeleeWeapon.valueOf(request.getArguments().toUpperCase());
            int quantityMelee = 0;
            HashSet<SpaceMarine> marinesMelee = collectionHandler.enumerationMelee(MeleeWep);
            if (!marinesMelee.isEmpty()) {
                for(SpaceMarine marine: marinesMelee) quantityMelee += 1;
                Console.println(quantityMelee);
            } else Console.println("Items matching the condition were not found!");
        } catch (IllegalArgumentException exception) {
            System.out.println("This element is not in the list!");
        }
    }
    }
}