package run;

import java.util.HashMap;
import java.util.Scanner;
import commands.*;
import requests.Request;
import utils.Console;
import utils.*;
import network.TCPServer;

public class App {


    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";
    public static void main(String[] args) {
        Scanner userScanner = new Scanner(System.in);
        final String nameFile = args[0];
        MarineAsker marineAsker = new MarineAsker(userScanner);
        FileManager fileManager = new FileManager(nameFile);
        CollectionHandler collectionHandler = new CollectionHandler(fileManager);
        TCPServer server = new TCPServer();

        Command info = new InfoCommand(collectionHandler, server);
        Command show = new ShowCommand(collectionHandler, server);
        Command add = new AddCommand(collectionHandler, marineAsker);
        Command update = new UpdateCommand(collectionHandler, marineAsker);
        Command removeById = new RemoveByIdCommand(collectionHandler, server);
        Command clear = new ClearCommand(collectionHandler);
        Command save = new SaveCommand(collectionHandler);
        Command exit = new ExitCommand();
        Command addIfMin = new AddIfMinCommand(collectionHandler, marineAsker);
        Command addIfMax = new AddIfMaxCommand(collectionHandler, marineAsker);
        Command removeLower = new RemoveLowerCommand(collectionHandler, server);
        Command averageOfHealth = new AverageOfHealthCommand(collectionHandler, server);
        Command filterStartsWithName = new FilterStartsWithNameCommand(collectionHandler, server);
        Command countGreaterThanMeleeWeapon = new CountGreaterThanMeleeWeaponCommand(collectionHandler, server);

        HashMap<String, Command> map = new HashMap<String, Command>();
        map.put(info.getName(), info);
        map.put(show.getName(), show);
        map.put(add.getName(), add);
        map.put(removeById.getName(), removeById);
        map.put(update.getName(), update);
        map.put(clear.getName(), clear);
        map.put(filterStartsWithName.getName(), filterStartsWithName);
        map.put(addIfMin.getName(), addIfMin);
        map.put(addIfMax.getName(), addIfMax);
        map.put(removeLower.getName(),removeLower);
        map.put(averageOfHealth.getName(), averageOfHealth);
        map.put(countGreaterThanMeleeWeapon.getName(), countGreaterThanMeleeWeapon);

        Command executeScriptCommand = new ExecuteScriptCommand(map);
        map.put(executeScriptCommand.getName(), executeScriptCommand);

        new Thread(() ->{
            while(true){
                String command = userScanner.nextLine();
                if(command.trim().equals(exit.getName())){
                    userScanner.close();
                    save.execute(new Request("save", "placeholderArg",null));
                    exit.execute(new Request("exit", "placeholderArg",null));
                }
                if(command.trim().equals(save.getName())){
                    save.execute(new Request("save", "placeholderArg",null));
                } else {
                    Console.printerror("No Such Command, server can run only save and exit commands");
                }
            }
        }).start();

        new Thread(() -> server.start(map,collectionHandler)).start();
    }
}